package com.wuda.tester.mysql.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于调节命令行参数,避免传入的命令行参数不合理.
 *
 * @author wuda
 */
public class CliArgsRegulator {

    private static Logger logger = LoggerFactory.getLogger(CliArgsRegulator.class);

    /**
     * 调节,校准参数.
     *
     * @param cliArgs 从命令行传递的参数
     */
    public static void regulate(CliArgs cliArgs) {
        regulateMaxItemPerUser(cliArgs);
        regulateThreadCount(cliArgs);
    }

    /**
     * 调节线程数.
     */
    public static void regulateThreadCount(CliArgs cliArgs) {
        int expectedThreadCount = cliArgs.getThread();
        int actualThreadCount = expectedThreadCount;
        int expectedUserCount = cliArgs.getUserCount();
        if (expectedUserCount <= cliArgs.getBatchSize()) {
            actualThreadCount = 1;
        } else if (expectedUserCount <= expectedThreadCount * cliArgs.getBatchSize()) {
            // 比如：总共只需要生成20000个用户,现在有10条线程,每个线程一次性生成10000条数据的情况
            actualThreadCount = expectedThreadCount / cliArgs.getBatchSize() + 1;
        }
        if(actualThreadCount != expectedThreadCount){
            logger.info("生成" + expectedUserCount + "个用户,不需要" + expectedThreadCount + "条线程,重新调整线程数为" + actualThreadCount);
        }
        cliArgs.setThread(actualThreadCount);
    }

    /**
     * 调节线程数.
     */
    public static void regulateMaxItemPerUser(CliArgs cliArgs) {
        int expected = cliArgs.getMaxItemPerUser();
        int actual = expected;
        if (expected >= cliArgs.getMaxItemPerUserExceed()) {
            actual = cliArgs.getMaxItemPerUserExceed();
            logger.info("每个用户的item数量为" + expected + ",太大,重新调整为" + actual);
        }
        cliArgs.setMaxItemPerUser(actual);
    }
}
