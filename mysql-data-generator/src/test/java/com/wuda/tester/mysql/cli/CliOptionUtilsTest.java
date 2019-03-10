package com.wuda.tester.mysql.cli;

import org.junit.Test;

public class CliOptionUtilsTest {

    @Test
    public void parser() {
//        String[] args = new String[]{"-h"};
        String[] args = new String[]{"--mysql-url=jdbc:mysql://localhost:3306/?serverTimezone=UTC",
                "--mysql-username=test",
                "--mysql-password=123456",
                "--mysql-max-connection=8",
                "--thread=30",
                "--user-count=100",
                "--max-item-per-user=4"};
        CliArgs cliArgs = CliOptionUtils.parser(CliOptionUtils.getOptions(), args);
        System.out.println(cliArgs);
    }
}
