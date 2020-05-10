package com.wuda.tester.mysql;

import lombok.Getter;
import org.jooq.InsertReturningStep;

/**
 * 表示向给定的表插入数据.记录插入数据的量,不是精确值,只是一个估计值.
 *
 * @author wuda
 */
@Getter
public class TableInsertion {

    private TableName tableName;
    private InsertReturningStep insertReturningStep;

    /**
     * 表中插入的数据量.估计值.
     */
    private int valuesCount;

    /**
     * 累加数据量.
     *
     * @param increment 数据增量
     */
    public void incrementValuesCount(int increment) {
        valuesCount += increment;
    }

    /**
     * 构造实例.
     *
     * @param tableName           表的名称
     * @param insertReturningStep 执行insert into的语句
     */
    public TableInsertion(TableName tableName, InsertReturningStep insertReturningStep) {
        this.tableName = tableName;
        this.insertReturningStep = insertReturningStep;
    }
}
