package com.xingoo.teddy.manager;

import com.xingoo.teddy.service.JobService;
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
    private TaskService taskService;

    @Autowired
    private JobService jobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("check db env ...");
//        if (taskService.count() < 0) {
//            logger.info("create table task.");
//            taskService.create();
//
//        }

        logger.info("check table job env ...");
        if(jobService.count() < 0){
            logger.info("create table job.");
            jobService.create();
        }
    }
}
