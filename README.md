# 简介
- 生成测试数据，总共有4个表，包括用户表(individual_user)，店铺表(shop)，仓库表(warehouse)，商品表(item)。生成的数据规模是可配置的，比如指定生成100万用户,5000万商品；并且**数据之间有关联关系**，因此可以测试sql join语句。
- 用于数据库压力测试


很多工具要么生成的数据是单表，即数据之间没有关联关系，要么数据量较小，对于很多测试看不到效果，本项目的目的就是既生成有关联关系的数据，又可以客制化数据规模！

# 数据量配置
查看 --user-count 和 --max-item-per-user 两个选项的说明

# 快速开始

```
安装java JDK , java版本 >= 1.8
```

```
下载可执行jar包
```
- [jar下载](https://github.com/wuda0112/mysql-tester/releases/)

```
生成数据库表,sql脚本如下
```
- [脚本文件](https://github.com/wuda0112/mysql-tester/blob/master/mysql_tester.sql)

```
输入命令，启动。默认连接到本地mysql，即: localhost:3306

java -jar mysql-tester-${VERSION}.jar --mysql-username=用户名 --mysql-password=密码
```

# 支持的参数(必须放在mysql-tester-${VERSION}.jar后面)

```
    --max-item-per-user <arg>
                                   每个用户最多有多少商品数;在生成数据时,随机为每个用户生成商品,数量取值范围是
                                   [0,MAX](default=10).比如默认生成10000个用户,每个用户
                                   最多10个商品,那么大致就可以知道生成的数据规模
    --mysql-max-connection <arg>   mysql最大连接数(default=25)
    --mysql-password <arg>         mysql password
    --mysql-url <arg>
                                   mysql连接url(default=jdbc:mysql://localho
                                   st:3306/?serverTimezone=UTC)
    --mysql-username <arg>         mysql username
    --thread <thread>              生成数据的线程数(default=50)
    --user-count <arg>
                                   用户表生成多少行记录,同时也是店铺表和仓库表的记录数,因为一个用户只拥有一个店
                                   铺和一个仓库(default=10000),当生成的记录数达到该值时,数据生成
                                   任务结束
```

