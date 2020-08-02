package com.wuda.tester.mysql.generate;

import com.wuda.tester.mysql.cli.CliArgs;

/**
 * 数据集,先在内存中准备好数据,然后这些数据再被插入数据库中.
 * 不是一次性把所有数据都生成了,如果要生成5000万数据,那么内存肯定溢出,
 * 可以多次生成{@link DataSet},只要最后总的数据量满足即可.
 * 通常由{@link com.wuda.tester.mysql.generate.DataGenerator.Producer}生成数据集,然后这些数据
 * 再由{@link com.wuda.tester.mysql.generate.DataGenerator.Consumer}消费,也就是插入数据库中.
 *
 * @author wuda
 */
public interface DataSet {

    /**
     * 准备好静态数据,这些数据将被插入到数据库中.
     *
     * @param cliArgs 包含了数据量参数,这样才知道每次生成多少数据量比较合适
     */
    void prepare(CliArgs cliArgs);
}
