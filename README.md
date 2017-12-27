# StreamingMonitor

> **项目背景** 公司的spark集群是CDH，暂时不能良好的支持spark2.2的streaming，所以考虑自己写一个监控程序。

## 2017-12-27

基于yarn的spark streaming任务管理服务，支持一下功能：

1. web端提交任务
2. streaming状态管理
3. streaming异常检测与邮件告警

前端参考：http://www.cssmoban.com/cssthemes/6836.shtml

## 未来计划

1. 支持任务的自动重启与手动重启
2. 丰富提交任务的界面
3. 前端框架重构，现在是基于bootstrap+jquery有点乱
4. 后段去掉基于线程的告警和检测服务
5. 支持任务的删除停止
6. 丰富告警类型
7. 支持在线streaming无缝升级