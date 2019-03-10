package com.wuda.tester.mysql.cli;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 命令行工具类.
 *
 * @author wuda
 */
public class CliOptionUtils {

    private static Logger logger = LoggerFactory.getLogger(CliOptionUtils.class);

    /**
     * 支持的选项.
     *
     * @return 选项
     */
    public static Options getOptions() {
        // 定义一个可选选项集
        Options options = new Options();
        options.addOption(Option.builder(CliArgs.HELP_SHORT)
                .longOpt(CliArgs.HELP)
                .argName(CliArgs.HELP)
                .desc(CliArgs.HELP_DESC)
                .build());
        options.addOption(Option.builder()
                .longOpt(CliArgs.THREAD)
                .argName(CliArgs.THREAD)
                .hasArg()
                .desc(CliArgs.THREAD_DESC)
                .build());
        options.addOption(Option.builder()
                .longOpt(CliArgs.MYSQL_URL)
                .hasArg()
                .desc(CliArgs.MYSQL_URL_DESC)
                .build());
        options.addOption(Option.builder()
                .longOpt(CliArgs.MYSQL_USERNAME)
                .hasArg()
                .desc(CliArgs.MYSQL_USERNAME_DESC)
                .build());
        options.addOption(Option.builder()
                .longOpt(CliArgs.MYSQL_PASSWORD)
                .hasArg()
                .desc(CliArgs.MYSQL_PASSWORD_DESC)
                .build());
        options.addOption(Option.builder()
                .longOpt(CliArgs.MYSQL_MAX_CONNECTION)
                .hasArg()
                .desc(CliArgs.MYSQL_MAX_CONNECTION_DESC)
                .build());
        options.addOption(Option.builder()
                .longOpt(CliArgs.USER_COUNT)
                .hasArg()
                .desc(CliArgs.USER_COUNT_DESC)
                .build());
        options.addOption(Option.builder()
                .longOpt(CliArgs.MAX_ITEM_PER_USER)
                .hasArg()
                .desc(CliArgs.MAX_ITEM_PER_USER_DESC)
                .build());

        return options;
    }

    /**
     * 解析命令行参数.如果解析失败,则直接退出jvm,即:{@link System#exit(int)}.
     *
     * @param options 支持的选项
     * @param args    命令行参数
     * @return {@link CliArgs}对象
     */
    public static CliArgs parser(Options options, String[] args) {
        // 初始化一个命令行解析器，一般用默认的就可以
        CommandLineParser parser = new DefaultParser();
        CliArgs cliArgs = new CliArgs();
        String cmdLineSyntax = "支持的选项";
        try {
            CommandLine commandLine = parser.parse(options, args);
            helpOption(options, commandLine, cmdLineSyntax);
            threadOption(commandLine, cliArgs);
            mysqlUrlOption(commandLine, cliArgs);
            mysqlUsernameOption(commandLine, cliArgs);
            mysqlPasswordOption(commandLine, cliArgs);
            mysqlMaxConnectionOption(commandLine, cliArgs);
            userCountOption(commandLine, cliArgs);
            maxItemPerUserOption(commandLine, cliArgs);
        } catch (Exception e) {
            // 因为本来就是java命令启动的程序,还有很多其他java自身的选项,
            // 最坏情况就是不识别的选项不处理而已
            HelpFormatter helpFormatter = new HelpFormatter();
            if (!e.getClass().equals(org.apache.commons.cli.UnrecognizedOptionException.class)) {
                helpFormatter.printHelp(cmdLineSyntax, options);
                System.out.println("\n命令行参数解析异常," + e.getMessage());
                exit0();
            } else {
                helpFormatter.printHelp(cmdLineSyntax, options);
                logger.warn(e.getMessage(), e);
            }
        }
        return cliArgs;
    }

    private static void helpOption(Options options, CommandLine commandLine, String cmdLineSyntax) {
        // 如果存在 -h --help 参数
        if (commandLine.hasOption(CliArgs.HELP)) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(cmdLineSyntax, options);
            exit0();
        }
    }

    private static void threadOption(CommandLine commandLine, CliArgs cliArgs) throws ParseException {
        if (commandLine.hasOption(CliArgs.THREAD)) {
            String optionValue = commandLine.getOptionValue(CliArgs.THREAD);
            if (StringUtils.isNotBlank(optionValue)) {
                String message = "选项" + CliArgs.THREAD + "只支持数字类型,并且必须大于0";
                try {
                    int thread = Integer.parseInt(optionValue);
                    if (thread <= 0) {
                        throw new ParseException(message);
                    }
                    cliArgs.setThread(thread);
                } catch (Exception e) {
                    throw new ParseException(message);
                }
            }
        }
    }

    private static void mysqlUrlOption(CommandLine commandLine, CliArgs cliArgs) {
        if (commandLine.hasOption(CliArgs.MYSQL_URL)) {
            String optionValue = commandLine.getOptionValue(CliArgs.MYSQL_URL);
            if (StringUtils.isNotBlank(optionValue)) {
                cliArgs.setMysqlUrl(optionValue);
            }
        }
    }

    private static void mysqlUsernameOption(CommandLine commandLine, CliArgs cliArgs) {
        if (commandLine.hasOption(CliArgs.MYSQL_USERNAME)) {
            String optionValue = commandLine.getOptionValue(CliArgs.MYSQL_USERNAME);
            if (StringUtils.isNotBlank(optionValue)) {
                cliArgs.setMysqlUsername(optionValue);
            }
        }
    }

    private static void mysqlPasswordOption(CommandLine commandLine, CliArgs cliArgs) {
        if (commandLine.hasOption(CliArgs.MYSQL_PASSWORD)) {
            String optionValue = commandLine.getOptionValue(CliArgs.MYSQL_PASSWORD);
            if (StringUtils.isNotBlank(optionValue)) {
                cliArgs.setMysqlPassword(optionValue);
            }
        }
    }

    private static void mysqlMaxConnectionOption(CommandLine commandLine, CliArgs cliArgs) throws ParseException {
        if (commandLine.hasOption(CliArgs.MYSQL_MAX_CONNECTION)) {
            String optionValue = commandLine.getOptionValue(CliArgs.MYSQL_MAX_CONNECTION);
            if (StringUtils.isNotBlank(optionValue)) {
                String message = "选项" + CliArgs.MYSQL_MAX_CONNECTION + "只支持数字类型,并且必须大于0";
                try {
                    int maxConnection = Integer.parseInt(optionValue);
                    if (maxConnection <= 0) {
                        throw new ParseException(message);
                    }
                    cliArgs.setMysqlMaxConnection(maxConnection);
                } catch (Exception e) {
                    throw new ParseException(message);
                }
            }
        }
    }

    private static void userCountOption(CommandLine commandLine, CliArgs cliArgs) throws ParseException {
        if (commandLine.hasOption(CliArgs.USER_COUNT)) {
            String optionValue = commandLine.getOptionValue(CliArgs.USER_COUNT);
            if (StringUtils.isNotBlank(optionValue)) {
                String message = "选项" + CliArgs.USER_COUNT + "只支持数字类型,并且必须大于0";
                try {
                    int userCount = Integer.parseInt(optionValue);
                    if (userCount <= 0) {
                        throw new ParseException(message);
                    }
                    cliArgs.setUserCount(userCount);
                } catch (Exception e) {
                    throw new ParseException(message);
                }
            }
        }
    }

    private static void maxItemPerUserOption(CommandLine commandLine, CliArgs cliArgs) throws ParseException {
        if (commandLine.hasOption(CliArgs.MAX_ITEM_PER_USER)) {
            String optionValue = commandLine.getOptionValue(CliArgs.MAX_ITEM_PER_USER);
            if (StringUtils.isNotBlank(optionValue)) {
                String message = "选项" + CliArgs.MAX_ITEM_PER_USER + "只支持数字类型,并且必须大于0";
                try {
                    int maxItemPerUser = Integer.parseInt(optionValue);
                    if (maxItemPerUser <= 0) {
                        throw new ParseException(message);
                    }
                    cliArgs.setMaxItemPerUser(maxItemPerUser);
                } catch (Exception e) {
                    throw new ParseException(message);
                }
            }
        }
    }

    private static void exit0() {
        System.exit(0);
    }
}
