package com.wuda.tester.mysql.generate;

import com.wuda.tester.mysql.cli.CliArgs;
import com.wuda.tester.mysql.statistic.DataGenerateStat;

/**
 * 全量数据生成器.
 *
 * @author wuda
 */
public class FullDataGenerator extends DataGenerator {


    /**
     * 构造实例.
     *
     * @param dataGenerateStat 数据生成过程的统计信息,所有的{@link DataGenerator}都必须共享同一个统计实例,
     *                         这样每个生成器才能知道整体情况
     * @param cliArgs          命令行参数
     */
    public FullDataGenerator(DataGenerateStat dataGenerateStat, CliArgs cliArgs) {
        super(cliArgs, dataGenerateStat, new FullDataGeneratorStopPolicy(cliArgs));
    }
}
