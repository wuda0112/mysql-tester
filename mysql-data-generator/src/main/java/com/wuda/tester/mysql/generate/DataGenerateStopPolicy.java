package com.wuda.tester.mysql.generate;

import com.wuda.tester.mysql.statistic.DataGenerateStat;

/**
 * 停止生成数据的策略.尤其是全量数据生成时,不会一直无限制生成, 需要一个停止策略,比如某一个表数据量达到100W条记录时停止.
 *
 * @author wuda
 */
public interface DataGenerateStopPolicy {

    /**
     * 根据统计信息决定是否可以停止了.
     *
     * @param stat 统计信息
     * @return true-如果可以停止了
     */
    boolean stop(DataGenerateStat stat);

    /**
     * 当数据生成任务退出时,即{@link #stop(DataGenerateStat)}=true时,
     * 给出具体的理由,为什么会做出停止的原因.
     *
     * @return 停止的原因
     */
    String getStopMessage();
}
