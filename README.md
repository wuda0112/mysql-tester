# 下载可执行JAR包，生成数据

```
1. 安装Java JDK, Java版本 >= 1.8
```

```
2. 下载可执行jar文件
```
- [jar下载](https://github.com/wuda0112/mysql-tester/releases/)

```
3. 生成数据库表,SQL文件和【相应版本的JAR在同一个地方下载】
```
- [mysql_tester.sql 脚步文件](https://github.com/wuda0112/mysql-tester/releases/)

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

# 简介
生成的数据规模是可配置的，比如指定生成100万用户,5000万商品；并且**数据之间有关联关系**，因此可以测试sql join等语句。
很多工具要么生成的数据是单表，即数据之间没有关联关系，要么数据量较小，对于很多测试看不到效果，本项目的目的就是既生成有关联关系的数据，又可以自定义数据规模！

# 数据库表
不同的版本，所使用的表可能不一样，因为一直在增加更多的表。越高的版本，所使用的表的数量越多，已经发布的版本所使用的表，请查看对应版本的SQL文件

## 最新发布版本所使用的表，如下
数据库表选自于我的另外一个中台项目[**foundation**](https://github.com/wuda0112/foundation)
### foundation_user
- - user,用户表
- - individual_user_general，个人用户基本信息
- - user_account,用户账号信息，适用各种类型的用户
- - user_email,用户的email
- - user_phone,用户的电话
### foundation_store
- - store，店铺表
- - store_general，店铺基本信息
- - store_user_relationship,如果把用户ID字段放在store表中，表明店铺属于某个用户，但是如果有多个用户可以管理这个店铺呢？有种做法是一个用户作为另一个用户的子账号；也可以建立用户与店铺的关联关系，这样感觉更符合逻辑。把用户IID放在store表，可以很明确的表明店铺的owner，如果是用关联关系表的话，就需要明确的标明哪个用户是owner，哪些用户只是管理这个店铺的。
### foundation_item
- - item，物品（商品）表
- - item_general，物品基本信息
- - item_description，物品描述信息
- - item_variation,物品规格
### foundation_commons
- - phone,电话表
- - email,邮箱表
- - property_key,属性的key
- - property_value,属性的值

# 数据量配置
查看 --user-count 和 --max-item-per-user 两个选项的说明

# 支持的参数(必须放在mysql-tester-${VERSION}.jar后面)

```
    --max-item-per-user <arg>
                                   每个用户最多有多少商品数;在生成数据时,随机为每个用户生成商品,数量取值范围是
                                   [0,MAX](default=10).比如默认生成10000个用户,每个用户
                                   最多10个商品,那么大致就可以知道生成的数据规模
    --mysql-max-connection <arg>   mysql最大连接数(default=25)，不是越大越好
    --mysql-password <arg>         mysql password
    --mysql-url <arg>
                                   mysql连接url(default=jdbc:mysql://localho
                                   st:3306/?serverTimezone=UTC)
    --mysql-username <arg>         mysql username
    --thread <thread>              生成数据的线程数(default=50)，不是越大越好
    --user-count <arg>
                                   用户表生成多少行记录,同时也是店铺表和仓库表的记录数,因为一个用户只拥有一个店
                                   铺和一个仓库(default=10000),当生成的记录数达到该值时,数据生成
                                   任务结束
```

