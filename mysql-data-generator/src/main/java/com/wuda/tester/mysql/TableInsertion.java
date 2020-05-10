package com.wuda.tester.mysql;

import lombok.Getter;
import org.jooq.InsertReturningStep;

/**
 * 表示向给定的表插入数据.
 *
 * @author wuda
 */
@Getter
public class TableInsertion {

    private TableName tableName;
    private InsertReturningStep insertReturningStep;

    private int values;

    /**
     * 累加数据量.
     *
     * @param increment 数据增量
     */
    public void incValues(int increment) {
        values += increment;
    }

    public TableInsertion(TableName tableName, InsertReturningStep insertReturningStep) {
        this.tableName = tableName;
        this.insertReturningStep = insertReturningStep;
    }
}
