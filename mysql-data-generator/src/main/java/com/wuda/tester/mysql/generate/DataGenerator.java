package com.wuda.tester.mysql.generate;

import com.wuda.tester.mysql.orm.ObjectRelationalMapper;
import com.wuda.tester.mysql.repository.MysqlRepository;
import com.wuda.tester.mysql.repository.OneTableOperation;
import com.wuda.tester.mysql.statistic.DataGenerateStat;
import com.wuda.yhan.code.generator.lang.TableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据生成器.每一个{@link DataGenerator}类的实例独享一个线程,
 * 在生成数据的过程中,使用{@link DataGenerateStat}统计,需要注意的是,
 * 如果所有的{@link DataGenerator}使用同一个{@link DataGenerateStat}统计实例,
 * 则所有生成器都会共享全局的统计信息,否则就只是当前生成器自己的统计信息.
 * 然后{@link DataGenerateStopPolicy}根据这些统计信息就可以判断是否停止生成数据, 确定可以停止后,启动的线程将会停止并退出.
 *
 * @author wuda
 */
public class DataGenerator {

    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(DataGenerator.class);

    /**
     * 执行任务的线程.
     */
    private Thread thread;
    /**
     * 生成器是否已经启动.
     */
    private boolean started;
    /**
     * 生成器是否已经停止.
     */
    private boolean stopped;
    /**
     * thread factory.
     */
    private static DefaultThreadFactory threadFactory = new DefaultThreadFactory();
    /**
     * 数据库访问对象.
     */
    private MysqlRepository repository;
    /**
     * ORM.
     */
    private ObjectRelationalMapper orm;
    /**
     * 数据生成过程中的统计信息.
     */
    private DataGenerateStat dataGenerateStat;
    /**
     * 停止数据生成策略.
     */
    private DataGenerateStopPolicy stopPolicy;

    /**
     * 构造实例.
     *
     * @param mysqlRepository  数据库访问对象
     * @param orm              查看{@link ObjectRelationalMapper}的定义
     * @param dataGenerateStat 数据生成过程的统计信息,所有的{@link DataGenerator}都必须共享同一个统计实例,
     *                         这样每个生成器才能知道整体情况
     * @param stopPolicy       停止生成数据的策略
     */
    protected DataGenerator(MysqlRepository mysqlRepository, ObjectRelationalMapper orm, DataGenerateStat dataGenerateStat,
                            DataGenerateStopPolicy stopPolicy) {
        this.orm = orm;
        this.repository = mysqlRepository;
        this.dataGenerateStat = dataGenerateStat;
        this.stopPolicy = stopPolicy;
    }

    /**
     * 启动线程,执行数据生成任务.如果多次调用该方法,会有以下情形
     * <ul>
     * <li>数据生成任务还没有启动,则会启动一个线程,然后开始任务</li>
     * <li>数据生成任务已经启动,则此方法调用不会产生任何影响</li>
     * <li>数据生成任务已经停止,抛出{@link AlreadyStoppedException}异常</li>
     * </ul>
     *
     * @throws AlreadyStoppedException 已经停止
     */
    public void generate() throws AlreadyStoppedException {
        if (started) {
            return;
        }
        if (stopped) {
            throw new AlreadyStoppedException();
        }
        Task task = new Task();
        thread = threadFactory.newThread(task);
        thread.start();
        started = true;
    }

    /**
     * 一次数据生成任务开始.
     */
    protected void oneGenTaskStar() {
        dataGenerateStat.incrementAndGetTotalTaskCount();
    }

    /**
     * 一次数据生成任务结束.
     */
    protected void oneGenTaskEnd() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName()).append("\n");
        builder.append("\tstarted=").append(started).append("\n");
        builder.append("\tstopped=").append(stopped).append("\n");
        if (thread != null) {
            builder.append("\tthread").append("=").append(thread.getName()).append("\n");
            builder.append("\tthread state:").append(thread.getState());
            StackTraceElement[] trace = thread.getStackTrace();
            for (StackTraceElement traceElement : trace)
                builder.append("\t ").append(traceElement);
        } else {
            builder.append("\tthread is null");
        }
        return builder.toString();
    }

    /**
     * 数据保存到数据库之前.
     */
    protected void beforeInsert(List<TableEntity> entities) {

    }

    /**
     * 数据保存到数据库.
     */
    protected void insertIntoTable(List<TableEntity> entities) {
        List<OneTableOperation> list = OneTableOperation.merge(entities);
        repository.insert(list);
    }

    /**
     * 数据保存到数据库之后.
     */
    protected void afterInsert(List<TableEntity> entities) {
        for (TableEntity entity : entities) {
            dataGenerateStat.insertedIncrementAndGet(entity.getClass());
        }
        dataGenerateStat.incrementAndGetSuccessTaskCount();
    }

    /**
     * 当异常发生时.
     *
     * @param throwable 具体的异常信息
     */
    protected void onException(Throwable throwable) {
        logger.warn(throwable.getMessage(), throwable);
        dataGenerateStat.incrementAndGetFailureTaskCount();
    }

    /**
     * 当数据生成任务停止后,做一些后续收尾操作.
     */
    protected void onStop() {
        Thread old = thread;
        old.interrupt();
        thread = null;
        stopped = true;
        String message = stopPolicy.getStopMessage();
        logger.info("数据生成任务停止!" + message);
    }

    /**
     * 处理数据生成任务.
     */
    class Task implements Runnable {

        @Override
        public void run() {
            while (!stopPolicy.stop(dataGenerateStat)) {
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()) {
                    logger.warn(currentThread.getName() + " interrupted");
                    break;
                }
                try {
                    oneGenTaskStar();
                    // 获取数据
                    List<TableEntity> entities = orm.insertTransaction();
                    beforeInsert(entities);
                    insertIntoTable(entities);
                    afterInsert(entities);
                    oneGenTaskEnd();
                } catch (Exception e) {
                    onException(e);
                }
            }
            onStop();
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
