package com.wuda.tester.mysql.cli;

import lombok.Data;
import lombok.ToString;

/**
 * 命令行接口输入的参数生成的实体.方便程序后续调用.
 *
 * @author wuda
 */
@Data
@ToString
public class CliArgs {

    final static String HELP = "help";
    final static String HELP_DESC = "print this help message";
    final static String HELP_SHORT = "h";

    final static String THREAD = "thread";
    final static int THREAD_DEFAULT = 50;
    final static String THREAD_DESC = "生成数据的线程数(default=" + THREAD_DEFAULT + ")";
    private int thread = THREAD_DEFAULT;

    final static String MYSQL_URL = "mysql-url";
    final static String MYSQL_URL_DEFAULT = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    final static String MYSQL_URL_DESC = "mysql连接url(default=" + MYSQL_URL_DEFAULT + ")";
    private String mysqlUrl = MYSQL_URL_DEFAULT;

    final static String MYSQL_USERNAME = "mysql-username";
    final static String MYSQL_USERNAME_DESC = "mysql username";
    private String mysqlUsername;

    final static String MYSQL_PASSWORD = "mysql-password";
    final static String MYSQL_PASSWORD_DESC = "mysql password";
    private String mysqlPassword;

    final static String MYSQL_MAX_CONNECTION = "mysql-max-connection";
    final static int MYSQL_MAX_CONNECTION_DEFAULT = THREAD_DEFAULT / 2;
    final static String MYSQL_MAX_CONNECTION_DESC = "mysql最大连接数(default=" + MYSQL_MAX_CONNECTION_DEFAULT + ")";
    private int mysqlMaxConnection = MYSQL_MAX_CONNECTION_DEFAULT;

    final static String USER_COUNT = "user-count";
    final static int USER_COUNT_DEFAULT = 10000;
    final static String USER_COUNT_DESC = "用户表生成多少行记录,同时也是店铺表的记录数,因为一个用户只拥有一个店铺" +
            "(default=" + USER_COUNT_DEFAULT + ")" +
            ",当生成的记录数达到该值时,数据生成任务结束";
    private int userCount = USER_COUNT_DEFAULT;

    final static String MAX_ITEM_PER_USER = "max-item-per-user";
    final static int MAX_ITEM_PER_USER_DEFAULT = 10;
    final static String MAX_ITEM_PER_USER_DESC = "每个用户最多有多少商品数;在生成数据时,随机为每个用户生成商品,数量取值范围是[0,MAX]" +
            "(default=" + MAX_ITEM_PER_USER_DEFAULT + ")." +
            "比如默认生成" + USER_COUNT_DEFAULT + "个用户,每个用户最多" + MAX_ITEM_PER_USER_DEFAULT + "个商品," +
            "那么大致就可以知道生成的数据规模";
    private int maxItemPerUser = MAX_ITEM_PER_USER_DEFAULT;


}
