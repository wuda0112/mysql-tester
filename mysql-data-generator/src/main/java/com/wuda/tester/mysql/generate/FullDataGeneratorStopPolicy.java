package com.wuda.tester.mysql.generate;

import com.wuda.tester.mysql.cli.CliArgs;
import com.wuda.tester.mysql.entity.IndividualUser;
import com.wuda.tester.mysql.statistic.DataGenerateStat;

/**
 * 全量数据生成的停止策略.
 *
 * @author wuda
 */
public class FullDataGeneratorStopPolicy implements DataGenerateStopPolicy {

    /**
     * 退出时,给出具体的描述信息.
     */
    private String stopMessage;

    /**
     * 命令行参数.
     */
    private CliArgs cliArgs;

    /**
     * 生成实例.
     *
     * @param cliArgs 命令行参数
     */
    public FullDataGeneratorStopPolicy(CliArgs cliArgs) {
        this.cliArgs = cliArgs;
    }

    @Override
    public boolean stop(DataGenerateStat stat) {
        if (stat.getInsertedCount(IndividualUser.class) >= cliArgs.getUserCount()) {
            stopMessage = "数据生成任务成功完成";
            return true;
        } else if (stat.getTotalTaskCount() > cliArgs.getThread() && stat.getFailureRate() > 0.1F) {
            stopMessage = "thread=" + cliArgs.getThread()
                    + ",totalTaskCount=" + stat.getTotalTaskCount()
                    + ",failureTaskCount=" + stat.getFailureTaskCount()
                    + ",失败率太高!";
            return true;
        }
        return false;
    }

    /**
     * 当数据生成任务退出时,即{@link #stop(DataGenerateStat)}=true时,
     * 给出具体的理由,为什么会做出停止的原因.
     *
     * @return 停止的原因
     */
    public String getStopMessage() {
        return stopMessage;
    }

}
