# Teddy 泰迪

> **项目背景** 公司的spark集群是CDH，暂时不能良好的支持spark2.2的streaming，所以考虑自己写一个监控程序。
至于为什么要起这样一个名字，一方面公司的项目大多以狗狗的名字命名，另外，自己还养了一只小泰迪（名字叫做  三月），希望自己也能用心的对待这个项目。

## 主要功能

1. Streaming任务部署
2. 任务监控与告警
3. 任务自启动
4. (new)任务资源自定义配置
5. (new)Spark Streaming代码零侵入

## 效果展示

![首页](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/4.jpg)
![任务配置](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/6.jpg)
![jar包管理](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/3.jpg)
![配置浏览](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/5.jpg)

## 使用说明

前提：

1. 部署web服务的机器上需要有spark（否则无法提交任务）
2. 部署web服务的机器上需要能使用app命令操作yarn(否则无法手动停止任务)

### 1 主要需要注意的参数

```
###########
# 环境配置 #
###########

# 本地spark的安装目录
spark.home=/var/lib/hadoop-hdfs/app/spark

# 本地资源的上传目录
lib.home=/home/xinghailong/monitor/lib/

# spark任务重定向日志文件，如果不嫌弃，可以直接重定向到日志文件
log.file=/home/xinghailong/monitor2/teddy/logs/teddy.log

# yarn的连接地址，用于Http方式查询spark任务的状态
yarn.cluster=hnode1:8088,hnode2:8088

# 邮件配置
mail.host=smtp.mxhichina.com
mail.from=report@abc.com
mail.passwd=123

###########
# 性能配置 #
###########

# 告警时间配置，秒单位，默认一分钟
# 注意：如果告警时间很短，小心邮件爆炸！
alert.interval=60

# 状态刷新时间配置，秒单位，默认5秒钟
state.refresh.interval=5

# 自动重启间隔时间，3分钟
# 注意：如果自定重启时间很短，可能会导致任务的重复启动
auto.restart.interval=180

# 尝试重启的次数
auto.restart.retries=3
```

### 2 启动teddy

下载代码后执行mvn install，即可编译打包项目工程。如果不想编译，也可以直接下载下面的版本：

待补充

编译后，在target目录下，可以找到对应的teddy-release压缩包。
目前仅支持Linux系统，修改相应的环境配置，启动即可。

## 链接

1. [httpClient快速入门](http://hc.apache.org/httpcomponents-client-4.5.x/quickstart.html)
2. [httpClient tutorial](http://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/index.html)
3. [spring boot](https://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/htmlsingle/)
4. [spring data jpa](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
5. [apache hadoop yarn](https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/NodeManagerRest.html#Application_API)
6. [jquery插件](http://www.jq22.com/jquery-plugins%E5%9E%82%E7%9B%B4%E5%AF%BC%E8%88%AA-1-jq)
7. [spark](http://spark.apache.org/docs/latest/)
8. [DERBY快速指南](http://db.apache.org/derby/docs/10.14/getstart/index.html)
9. [derby用户手册](http://db.apache.org/derby/docs/10.14/ref/index.html)
10. [derby开发者指南](http://db.apache.org/derby/docs/10.14/devguide/index.html)
