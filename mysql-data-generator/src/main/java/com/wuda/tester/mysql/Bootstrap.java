package com.wuda.tester.mysql;

import com.wuda.tester.mysql.cli.CliArgs;
import com.wuda.tester.mysql.cli.CliArgsRegulator;
import com.wuda.tester.mysql.cli.CliOptionUtils;
import com.wuda.tester.mysql.config.DatabaseConfiguration;
import com.wuda.tester.mysql.generate.DataGenerator;
import com.wuda.tester.mysql.generate.FoundationBasedDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

/**
 * 程序启动类.以SpringBoot项目方式启动.
 *
 * @author wuda
 */
@SpringBootApplication
public class Bootstrap {

    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        // 解析命令行参数
        CliArgs cliArgs = CliOptionUtils.parser(CliOptionUtils.getOptions(), args);
        cliArgs.validate();
        CliArgsRegulator.regulate(cliArgs);
        // spring boot
        ConfigurableApplicationContext context = SpringApplication.run(Bootstrap.class, args);
        SpringApplicationContextHolder.setApplicationContext(context);
        // 使用命令行传过来的数据库参数
        DatabaseConfiguration.applyArgs(context.getBean(DataSource.class), cliArgs);
        DataSource dataSource = SpringApplicationContextHolder.getApplicationContext().getBean(DataSource.class);

        // 全量数据生成器
        DataGenerator generator = new FoundationBasedDataGenerator(cliArgs, dataSource);
        generator.startup();
    }
}
