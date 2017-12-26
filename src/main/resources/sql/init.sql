-- streaming monitor
create table `streaming_task`(
  `id` varchar(100) not null,
  `name` varchar(200) not null comment '任务名字',
  `command` varchar(3000) not null comment '启动命令',
  `createTime` timestamp not null comment '修改时间',
  `modify_time` timestamp not null comment '修改时间',
  PRIMARY KEY (`id`)
)ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='streaming任务表';

-- http://hnode2:8088/ws/v1/cluster/apps/application_1509601523778_68428
create table `streaming_monitor`
(
  `id` varchar(200) not null comment 'application_id',
  `task_id` varchar(200) not null comment '关联的task id',
  `web_url` varchar(50) not null,
  `applicationType` varchar(20),
  `startedTime` timestamp,
  `finishedTime` timestamp,
  `allocatedMB` int(20),
  `allocatedVCores` int(20),
  `runningContainers` int(20),
  `memorySeconds` int(20),
  `vcoreSeconds` int(20),
  `modify_time` timestamp default CURRENT_TIMESTAMP not null comment '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='streaming application监控表';