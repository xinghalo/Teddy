# StreamingMonitor

> **项目背景** 公司的spark集群是CDH，暂时不能良好的支持spark2.2的streaming，所以考虑自己写一个监控程序。

## V1.0 需求

1. streaming 7*24小时的监控，如果出现故障就发送邮件
2. 支持任务的查看与spark UI的跳转


