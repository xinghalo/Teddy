package com.xingoo.teddy.manager;

import com.xingoo.teddy.entity.Restart;
import com.xingoo.teddy.mapper.*;
import com.xingoo.teddy.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DerbyInit implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private AlertMapper alertMapper;

    @Autowired
    private RestartMapper restartMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            logger.info("check derby table.");
            jobMapper.count();
        } catch (Exception e){
            logger.info("create table job.");
            jobMapper.create();
            logger.info("create table task.");
            taskMapper.create();
            logger.info("create table config.");
            configMapper.create();
            logger.info("create table alert.");
            alertMapper.create();
            logger.info("create table restart.");
            restartMapper.create();
        }
    }
}
