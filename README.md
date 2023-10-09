# 工程简介



# 项目启动步骤



## 一、创建数据库

1. 将db文件夹下的sql文件在mysql数据库中执行
2. you_cloud.sql是项目数据库
3. you_nacos_config.sql是nacos配置数据



## 二、启动nacos

1. 按照doc/nacos目录下的nacos文档配置nacos
2. 将nacos数据库指向步骤一中的you_naocs_config数据库
3. windows下执行nacos/bin目录下的startup.cmd
4. 启动后浏览器访问http://127.0.0.1:8848/nacos 进入nacos管理页面。默认用户名和密码都是nacos

## 三、启动sentinel控制台

1. 启动sentinel控制台jar包  
   在控制台运行下面启动代码 指定端口号

```shell
   java -Dserver.port=8718 -Dcsp.sentinel.dashboard.server=localhost:8718 -Dproject.name=sentinel-dashboard -jar /Users/apple/Documents/SpringCloudAlibaba/sentinel-dashboard/sentinel-dashboard-1.8.6.jar
```

## 四、启动you-gateway网关模块

## 五、启动you-auth认证中心模块

## 六、启动you-system系统模块
