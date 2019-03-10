package com.wuda.tester.mysql.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * mysql数据库访问类.
 *
 * @author wuda
 */
@Repository
public class MysqlRepositoryImpl implements MysqlRepository {

    @Override
    public void insert(List<OneTableOperation> tablesOperation) {
        for (OneTableOperation oneTableOperation : tablesOperation) {
            oneTableOperation.insert();
        }
    }
}
