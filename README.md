# 简介
- 生成测试数据,总共有4个表,包括用户表(individual_user)，店铺表(shop),仓库表(warehouse),商品表(item)。生成的数据规模是可配置的,比如指定生成100万用户,并且**数据之间有关联关系**,因此可以测试sql join语句。
- 用于数据库压力测试

# 快速开始

```
下载可执行jar包,下载链接
```

```
生成数据库表,sql脚本查看
```

```
输入命令，启动.

java -jar mysql-tester-${VERSION}.jar --mysql-username=用户名 --mysql-password=密码
```