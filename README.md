# Teddy 泰迪

> **项目背景** 公司的spark集群是CDH，暂时不能良好的支持spark2.2的streaming，所以考虑自己写一个监控程序。
至于为什么要起这样一个名字，一方面公司的项目大多以狗狗的名字命名，另外，自己还养了一只小泰迪（名字叫做  三月），希望自己也能用心的对待这个项目。

![首页样式](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/Jietu20171228-162819.jpg)

## 使用说明

### 1 创建表结构
```sql
create table `streaming_task`(
  `id`              varchar(100)  not null,
  `name`            varchar(200)  not null comment '任务名字',
  `command`         varchar(3000) not null comment '启动命令',
  `create_time`     long          not null comment '修改时间',
  `application_id`  varchar(200)  comment 'spark应用id',
  `web_url`         varchar(200)  comment 'url',
  `state`           varchar(20)   comment '状态',
  `email`           varchar(500)  comment '邮件',
  `is_send_email`   int(10)       comment '是否发送邮件',
  `modify_time`     timestamp     not null comment '修改时间',
  PRIMARY KEY (`id`)
)ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='streaming任务表';
```
### 2 在Streaming代码中加入下面的处理逻辑

```scala
import java.sql.DriverManager

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Duration, StreamingContext}

/**
  * 这个类用于关联穿入的task_id与application_id，因此需要强制占用几个参数：
  *
  * 0:  app_name        应用名字
  * 1:  task_id         任务id
  * 2:  default_web_ui  默认的url
  * 3:  class_name      驱动类的名字
  * 4:  jdbc_url        数据库的url
  * 5:  jdbc_user       数据库的用户名
  * 6:  jdbc_passwd     数据库的密码
  *
  */
object StreamingMonitor{

  def createContext(args:Array[String],duration:Duration):StreamingContext = {
    println("*************** 创建StreamingContext begin **************")

    args.foreach(println(_))

    if(args.length < 7) throw new IllegalArgumentException

    val app_name        = args(0)
    val task_id         = args(1)
    val default_web_ui  = args(2)
    val class_name      = args(3)
    val jdbc_url        = args(4)
    val jdbc_user       = args(5)
    val jdbc_passwd     = args(6)


    System.setProperty("hive.metastore.uris", "thrift://hnode1:9083")

    val sparkConf = new SparkConf().setAppName(args(0))
    val ssc = new StreamingContext(sparkConf,duration)
    ssc.sparkContext.setLogLevel("warn")

    val app_id = ssc.sparkContext.applicationId
    val web_url = ssc.sparkContext.uiWebUrl.getOrElse(s"'$default_web_ui'")

    // 连接数据库写入application_id等信息
    updateAppId(app_id, web_url, args(1))

    println("*************** 创建StreamingContext end **************")

    ssc
  }

  def updateAppId(app_id:String, web_url:String, task_id:String): Unit ={
    Class.forName("com.mysql.jdbc.Driver")
    val conn = DriverManager.getConnection("jdbc:mysql://10.10.5.11:3306/recommend", "xinhailong", "test!@3$xhl")
    val stmt = conn.createStatement

    val sql=
      s"""
         |update
         |  streaming_task
         |set
         |  application_id  = '$app_id',
         |  web_url         = '$web_url',
         |  state           = 'DRIVER_INIT'
         |where id = '$task_id'
         """.stripMargin

    println(sql)

    val count = stmt.executeUpdate(sql)
    if(count != 1) throw new IllegalStateException("向streaming_task表中写入app_id失败，请查看相关表，是否存在task_id")
    conn.close()
  }

}
```

使用的时候，直接基于他创建StreamingContext即可。
```scala
import com.alibaba.fastjson.JSON
import com.tgou.data.stanford.streaming.StreamingMonitor
import kafka.serializer.StringDecoder
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.kafka.KafkaUtils

object MonitoringTest {
  def main(args: Array[String]): Unit = {

    val ssc = StreamingMonitor.createContext(args,Seconds(5))

    // Create direct kafka stream with brokers and topics
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> "hnode9:9092,hnode10:9092",
      "enable.auto.commit" -> "true")

    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, Set("tgs-topic"))


    val lines = messages.map(_._2).map(line => JSON.parseObject(line))

    lines.print()

    ssc.start()

    ssc.awaitTermination()
  }
}
```
如果使用了检查点，可以这样：
```scala
def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getOrCreate(CHECKPOINT_PATH,() => createContext(args))
    ssc.start()
    ssc.awaitTermination()
  }

  def createContext(args:Array[String]): StreamingContext = {


    /**************************************************************
    * 1. 创建StreamingContext，并完成注册的监控                      *
    * ************************************************************/

    val ssc = StreamingMonitor.createContext(args,WINDOW_TIME)
    ...
```
### 3 启动StreamingMonitor

start.sh
```sbtshell
nohup java -jar streaming-monitor.jar &
```

stop.sh
```sbtshell
#!/bin/bash
# https://www.cnblogs.com/lovychen/p/6211209.html
PID=`ps -ef | grep streaming-monitor|grep -v grep | awk '{print $2}'`
kill -9 $PID
```

### 4 开启任务

点击任务管理，配置任务即可。

## 2017-12-27

基于yarn的spark streaming任务管理服务，支持一下功能：

1. web端提交任务
2. streaming状态管理
3. streaming异常检测与邮件告警

前端参考：http://www.cssmoban.com/cssthemes/6836.shtml

## 开发中..特性

### 2017-12-28 进度

- ~~1. 支持任务的自动重启与手动重启~~
- ~~4. 后段去掉基于线程的告警和检测服务~~ 改用ScheduledThreadPoolExecutor代替
- ~~5. 支持任务的删除停止~~

1. 增加任务界面的轮训查看
1. 丰富提交任务的界面
2. 前端框架重构，现在是基于bootstrap+jquery有点乱
3. 丰富告警类型
4. 支持在线streaming无缝升级
5. 增加使用说明文档

## 笔记

spark streaming基于yarn的任务状态查询：http://ip:port/ws/v1/cluster/apps/application_1509601523778_68428

## 链接

1. [httpClient快速入门](http://hc.apache.org/httpcomponents-client-4.5.x/quickstart.html)
2. [httpClient tutorial](http://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/index.html)
3. [spring boot](https://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/htmlsingle/)
4. [spring data jpa](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
5. [apache hadoop yarn](https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/NodeManagerRest.html#Application_API)
6. [jquery插件](http://www.jq22.com/jquery-plugins%E5%9E%82%E7%9B%B4%E5%AF%BC%E8%88%AA-1-jq)
7. [spark](http://spark.apache.org/docs/latest/)
