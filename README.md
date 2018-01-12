# Teddy 泰迪

> **项目背景** 公司的spark集群是CDH，暂时不能良好的支持spark2.2的streaming，所以考虑自己写一个监控程序。
至于为什么要起这样一个名字，一方面公司的项目大多以狗狗的名字命名，另外，自己还养了一只小泰迪（名字叫做  三月），希望自己也能用心的对待这个项目。

## 主要功能

1. Streaming任务部署
2. 任务监控与告警
3. 任务自启动

## 效果展示

![首页](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/1.jpg)
![任务配置](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/2.jpg)
![jar包管理](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/3.jpg)

## 使用说明

### 1 主要需要注意的参数

```
# 发布地址
server.address=0.0.0.0
server.port=18081

# 注册地址
register.url=http://hnode10:18081/task/register

# jar包上传目录
com.xingoo.streaming.monitor.resource.path=/home/xinghailong/monitor/lib/

# 告警时间配置，秒单位，默认一分钟
alert.interval=60

# 状态刷新时间配置，秒单位，默认5秒钟
state.refresh.interval=10

# 自动重启间隔时间，3分钟
auto.restart.interval=60

# derby数据库url
spring.datasource.url=jdbc:derby:/home/xinghailong/monitor/db;create=true  
```

### 2 在Streaming代码中加入下面的处理逻辑

```scala
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Duration, StreamingContext}

/**
  * 注册SparkStreaming
  */
object StreamingMonitor{

  /**
    * 创建streamingContext
    *
    * @param args     参数
    * @param duration 窗口
    * @param name     名字
    * @return
    */
  def createContext(args:Array[String],duration:Duration,name:String):StreamingContext = {
    val sparkConf = new SparkConf().setAppName(name)
    createContext(args,sparkConf,duration)
  }

  /**
    * 创建streamingContext
    *
    * @param args     参数
    * @param conf     配置
    * @param duration 窗口
    * @return
    */
  def createContext(args:Array[String], conf: SparkConf, duration:Duration):StreamingContext = {
    println("*************** register begin **************")

    println("args:")
    args.foreach(println)
    println()

    System.setProperty("hive.metastore.uris", "thrift://hnode1:9083")

    val ssc = new StreamingContext(conf,duration)
    ssc.sparkContext.setLogLevel("warn")

    val appId = ssc.sparkContext.applicationId
    val url = ssc.sparkContext.uiWebUrl.get
    val taskId = args(args.length-1)
    val register = args(args.length-2)

    val httpUrl = s"$register?taskId=$taskId&appId=$appId&state=REGISTER&url=$url"

    println(s"注册URL地址为:$httpUrl")

    val response = new DefaultHttpClient().execute(new HttpGet(httpUrl))
    if(response.getStatusLine.getStatusCode!=200){
      throw new Exception("注册失败")
    }

    println("*************** register end **************")

    ssc
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

    val ssc = StreamingMonitor.createContext(args,Seconds(5), "MonitoringTest")

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

    val ssc = StreamingMonitor.createContext(args,WINDOW_TIME,MonitoringTest)
    ...
```
### 3 启动StreamingMonitor

start.sh
```sbtshell
nohup java -jar teddy.jar &
```

stop.sh
```sbtshell
#!/bin/bash
PID=`ps -ef | grep teddy|grep -v grep | awk '{print $2}'`
kill -9 $PID
```

### 4 开启任务

点击任务管理，配置任务即可。

## 开发进度

### 2017-12-27

基于yarn的spark streaming任务管理服务，支持一下功能：

1. web端提交任务
2. streaming状态管理
3. streaming异常检测与邮件告警

前端参考：http://www.cssmoban.com/cssthemes/6836.shtml

### 2017-12-28 进度

- ~~1. 支持任务的自动重启与手动重启~~
- ~~4. 后段去掉基于线程的告警和检测服务~~ 改用ScheduledThreadPoolExecutor代替
- ~~5. 支持任务的删除停止~~

### 2018-01-08 

- ~~重构前端界面~~
- ~~增加拖拽上传控件~~ 参考：http://www.htmleaf.com/jQuery/Form/201510142663.html

### 2018-01-12

- 增加任务界面的轮训查看
- 丰富提交任务的界面
- 前端框架重构，现在是基于bootstrap+jquery有点乱
- 增加使用说明文档
- 底层基于derby+mybatis+druid
- 支持任务自动重启

### 待完成

3. 丰富告警类型
4. 支持在线streaming无缝升级

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
