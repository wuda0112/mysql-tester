package com.wuda.tester.mysql.statistic;

import com.wuda.tester.mysql.orm.ObjectRelationalMapper;
import com.wuda.yhan.code.generator.lang.TableEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据生成过程中的统计信息.该类不是线程安全的,所以统计信息不是精确的, 但是也不会太离谱,尽可能会减小误差.
 *
 * @author wuda
 */
public class DataGenerateStat {

    /**
     * 数据生成任务执行的总次数.
     */
    private AtomicInteger totalTaskCount = new AtomicInteger();
    /**
     * 数据生成任务成功的次数.即{@link ObjectRelationalMapper#insertTransaction()}的数据被
     * 成功保存到数据库的次数.
     */
    private AtomicInteger successTaskCount = new AtomicInteger();
    /**
     * 数据生成任务失败的次数.
     */
    private AtomicInteger failureTaskCount = new AtomicInteger();

    /**
     * 已经保存到数据库的实体的数量.key-entity,value-数量
     */
    private Map<Class<? extends TableEntity>, AtomicInteger> inserted_entity_count = new HashMap<>();

    /**
     * 获取给定entity的已经插入到数据库的数量.
     *
     * @param clazz entity class
     * @return 数量
     */
    public <T extends TableEntity> int getInsertedCount(Class<T> clazz) {
        AtomicInteger counter = inserted_entity_count.get(clazz);
        if (counter != null) {
            return counter.get();
        }
        return 0;
    }

    /**
     * 给定的entity数量加一,并且返回增加后的值.
     *
     * @param clazz entity class
     * @return 增加后的数量
     */
    public <T extends TableEntity> int insertedIncrementAndGet(Class<T> clazz) {
        AtomicInteger counter = inserted_entity_count.get(clazz);
        if (counter != null) {
            return counter.incrementAndGet();
        }
        counter = new AtomicInteger(1);
        inserted_entity_count.put(clazz, counter);
        return counter.get();
    }

    /**
     * 增加并且获取{@link #totalTaskCount}.
     *
     * @return int
     */
    public int incrementAndGetTotalTaskCount() {
        return totalTaskCount.incrementAndGet();
    }

    /**
     * 获取{@link #totalTaskCount}.
     *
     * @return int
     */
    public int getTotalTaskCount() {
        return totalTaskCount.get();
    }

    /**
     * 增加并且获取{@link #successTaskCount}.
     *
     * @return int
     */
    public int incrementAndGetSuccessTaskCount() {
        return successTaskCount.incrementAndGet();
    }

    /**
     * 获取{@link #successTaskCount}.
     *
     * @return int
     */
    public int getSuccessTaskCount() {
        return successTaskCount.get();
    }

    /**
     * 增加并且获取{@link #failureTaskCount}.
     *
     * @return int
     */
    public int incrementAndGetFailureTaskCount() {
        return failureTaskCount.incrementAndGet();
    }

    /**
     * 获取{@link #failureTaskCount}.
     *
     * @return int
     */
    public int getFailureTaskCount() {
        return failureTaskCount.get();
    }

    /**
     * 获取失败率.
     *
     * @return 失败率
     */
    public float getFailureRate() {
        int totalPlus1 = totalTaskCount.get() + 1;
        return (float) failureTaskCount.get() / totalPlus1;
    }
}
