# 重要：master分支的代码，使用了我的另外一个中台项目[**foundation**](https://github.com/wuda0112/foundation)该项目还没上传到Maven仓库中心，所以需要先把该项目clone下来

# 简介
- 生成测试数据，总共有7个表，它们是
- - user,用户表
- - individual_user_general，个人用户基本信息
- - store，店铺表
- - store_general，店铺基本信息
- - item，物品（商品）表
- - item_general，物品基本信息
- - item_description，物品描述信息
- 数据库表选自于我的另外一个中台项目[**foundation**](https://github.com/wuda0112/foundation)
- 数据库ER图
![如果看不见图片，需要翻墙](https://github.com/wuda0112/mysql-tester/blob/master/mysql_tester_ER.svg)

- 生成的数据规模是可配置的，比如指定生成100万用户,5000万商品；并且**数据之间有关联关系**，因此可以测试sql join等语句。
- 用于数据库压力测试


很多工具要么生成的数据是单表，即数据之间没有关联关系，要么数据量较小，对于很多测试看不到效果，本项目的目的就是既生成有关联关系的数据，又可以自定义数据规模！

# 数据量配置
查看 --user-count 和 --max-item-per-user 两个选项的说明

# 快速开始

```
1. 安装Java JDK, Java版本 >= 1.8
```

```
2. 下载可执行jar文件
```
- [jar下载](https://github.com/wuda0112/mysql-tester/releases/)

```
3. 生成数据库表,sql脚本如下
```
- [Create Table 脚本文件](https://github.com/wuda0112/mysql-tester/blob/master/mysql_tester.sql)

- [1.0.1版本的数据库(不推荐)](https://github.com/wuda0112/mysql-tester/blob/master/mysql_tester_1.0.1.sql)

```
4. 输入命令，启动。默认连接到本地mysql，即: localhost:3306，最简单的就是

java -jar mysql-tester-${VERSION}.jar --mysql-username=用户名 --mysql-password=密码
```

# clone项目
```aidl
1. 启动类: com.wuda.tester.mysql.Bootstrap
2. 启动之前必须配置 --mysql-username 和　--mysql-password　两个args，默认连接到本地mysql数据库,比如对于IDEA开发工具，
输入命令行参数的位置是：Run -> Edit Configuritions -> Configration -> Program arguments ,在输入框中输入
--mysql-username=your username --mysql-password=your password 即可
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

