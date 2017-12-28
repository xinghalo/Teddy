# StreamingMonitor

> **项目背景** 公司的spark集群是CDH，暂时不能良好的支持spark2.2的streaming，所以考虑自己写一个监控程序。

![首页样式](https://raw.githubusercontent.com/xinghalo/StreamingMonitor/master/description/img/Jietu20171228-162819.jpg)

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
