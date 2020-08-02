package com.wuda.tester.mysql.generate;

import com.wuda.tester.mysql.cli.CliArgs;
import com.wuda.tester.mysql.statistic.DataGenerateStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据生成器,随机生成数据并且插入数据库.在生成数据的过程中,使用{@link DataGenerateStat}统计,
 * 然后{@link DataGenerateStopPolicy}根据这些统计信息就可以判断是否停止生成数据,
 * 确定可以停止后,启动的线程将会停止并退出.
 *
 * @param <T> 具体的{@link DataSet}的类型
 * @author wuda
 */
public abstract class DataGenerator<T extends DataSet> {

    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(DataGenerator.class);
    /**
     * thread factory.
     */
    private static DefaultThreadFactory threadFactory = new DefaultThreadFactory();
    /**
     * 数据生成过程中的统计信息.
     */
    protected DataGenerateStat dataGenerateStat = new DataGenerateStat();
    /**
     * 停止数据生成策略.
     */
    private DataGenerateStopPolicy stopPolicy;

    private CliArgs cliArgs;
    private BlockingQueue<T> queue;
    protected DataSource dataSource;

    /**
     * 系统启动时间.
     */
    private Date startupTime;

    /**
     * 构造实例.
     *
     * @param cliArgs    命令行参数
     * @param dataSource datasource
     */
    public DataGenerator(CliArgs cliArgs, DataSource dataSource) {
        this.cliArgs = cliArgs;
        this.stopPolicy = new DefaultDataGeneratorStopPolicy(cliArgs);
        this.queue = new LinkedBlockingQueue<>(cliArgs.getThread());
        this.dataSource = dataSource;
    }

    /**
     * 正在运行的Consumer的数量.
     */
    private AtomicInteger runningConsumerCount = new AtomicInteger(0);
    /**
     * 正在运行的Producer的数量.
     */
    private AtomicInteger runningProducerCount = new AtomicInteger(0);

    /**
     * 准备数据.
     *
     * @param cliArgs 包含了数据量参数,这样才知道每次生成多少数据量比较合适
     * @return data set
     */
    public abstract T prepareDataSet(CliArgs cliArgs);

    /**
     * 将准备好的数据插入到数据库中.
     *
     * @param dataSet 准备好的数据
     */
    public abstract void insert(T dataSet);


    /**
     * 根据总的线程数,分配Producer占用的线程数.
     *
     * @param totalThreadSize 总的线程数
     * @return Producer的线程数
     */
    public static int allocateProducerThreadSize(int totalThreadSize) {
        float producerThreadSize = totalThreadSize * 0.01F;
        return (int) Math.ceil(producerThreadSize);
    }

    /**
     * 线程sleep.当catch到{@link InterruptedException}异常,使用{@link RuntimeException}的方式将异常抛出.
     *
     * @param millis millis
     */
    public void threadSleepSilence(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException("线程sleep时收到了InterruptedException", e);
        }
    }

    /**
     * 启动生产者,消费者,监控线程.
     */
    public void startup() {
        startupTime = new Date();
        int producerSize = allocateProducerThreadSize(cliArgs.getThread());
        int consumerSize = cliArgs.getThread() - producerSize;
        for (int i = 0; i < producerSize; i++) {
            threadFactory.newThread(new Producer("Producer-" + i)).start();
        }
        threadSleepSilence(30 * 1000); // 让producer先产生下数据
        for (int i = 0; i < consumerSize; i++) {
            threadFactory.newThread(new Consumer("Consumer-" + i)).start();
        }
        MonitorThread monitorThread = new MonitorThread();
        monitorThread.setDaemon(true);
        monitorThread.start();

        while (runningConsumerCount.get() > 0 || runningProducerCount.get() > 0) {
            threadSleepSilence(60 * 1000);
        }
    }

    /**
     * the Producer.
     *
     * @author wuda
     */
    public class Producer implements Runnable {

        /**
         * Producer的名称.
         */
        private String name;

        /**
         * 构造指定名称的Producer.
         *
         * @param name 名称
         */
        public Producer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            runningProducerCount.incrementAndGet();
            while (!stopPolicy.stop(dataGenerateStat)) {
                T dataSet = prepareDataSet(cliArgs);
                try {
                    queue.put(dataSet);
                } catch (InterruptedException e) {
                    logger.warn("producer = {} Interrupted", name);
                }
            }
            runningProducerCount.decrementAndGet();
            if (runningProducerCount.get() <= 0) {
                logger.info(dataGenerateStat.toString());
            }
            logger.info("Producer ={} 退出,{}", name, stopPolicy.getStopMessage());
        }
    }

    /**
     * the Consumer.
     *
     * @author wuda
     */
    public class Consumer implements Runnable {

        /**
         * consumer的名称.
         */
        private String name;

        /**
         * 构造指定名称的Consumer.
         *
         * @param name 名称
         */
        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            runningConsumerCount.incrementAndGet();
            T dataSet = null;
            while ((dataSet = queue.poll()) != null || runningProducerCount.get() > 0) {
                if (dataSet != null) {
                    try {
                        dataGenerateStat.incrementAndGetTotalTaskCount();
                        insert(dataSet);
                        dataGenerateStat.incrementAndGetSuccessTaskCount();
                    } catch (Exception e) {
                        logger.error("Consumer = {},生成数据异常", name, e);
                        dataGenerateStat.incrementAndGetFailureTaskCount();
                    }
                } else {
                    logger.info("queue没有获取到元素,可以考虑加大Producer数量,Consumer count ={} ,Producer count = {}"
                            , runningConsumerCount.get(), runningProducerCount.get());
                }
            }
            runningConsumerCount.decrementAndGet();
            logger.info("Consumer ={} 退出 ", name);
        }
    }

    /**
     * 用于监控生成数据的情况.
     *
     * @author wuda
     */
    public class MonitorThread extends Thread {

        @Override
        public void run() {
            while (runningConsumerCount.get() > 0 || runningProducerCount.get() > 0) {
                logger.info("启动时间: {}", startupTime);
                logger.info("DataSet queue中暂存的DataSet数量是:{},Consumer Count = {},Producer Count = {}"
                        , queue.size(), runningConsumerCount.get(), runningProducerCount.get());
                logger.info(dataGenerateStat.toString());
                threadSleepSilence(60 * 1000);
            }
            logger.info("monitor thread exit");
        }

    }

    /**
     * The default thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "generator-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
