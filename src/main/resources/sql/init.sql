create table `streaming_task`(
  `id` varchar(100) not null,
  `name` varchar(200) not null comment '任务名字',
  `command` varchar(3000) not null comment '启动命令',
  `create_time` LONG not null comment '修改时间',
  `application_id` varchar(200),
  `web_url` varchar(200) comment 'url',
  `state` VARCHAR(20) comment '状态',
  `email` VARCHAR(500) comment '邮件',
  `is_send_email` int(10) comment '是否发送邮件',
  `modify_time` timestamp not null comment '修改时间',
  PRIMARY KEY (`id`)
)ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='streaming任务表';
