package com.wuda.tester.mysql.repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * mysql数据库访问类.
 *
 * @author wuda
 */
public interface MysqlRepository {

    /**
     * 在同一个事物将数据保存到据库中.
     *
     * @param tablesOperation list of table operation
     */
    @Transactional(rollbackFor = Throwable.class)
    void insert(List<OneTableOperation> tablesOperation);

}
