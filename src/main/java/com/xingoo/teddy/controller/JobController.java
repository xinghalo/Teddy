package com.xingoo.teddy.controller;

import com.xingoo.teddy.entity.Job;
import com.xingoo.teddy.service.JobService;
import com.xingoo.teddy.utils.Response;
import org.apache.spark.launcher.SparkAppHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("job")
public class JobController {

    private ConcurrentHashMap<Integer,SparkAppHandle> cache = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobService jobService;

    @Transactional
    @RequestMapping("start")
    public Response start(@RequestBody Job job){

        try {
            SparkAppHandle handler = jobService.start(job);
        } catch (IOException e) {
            logger.error("job start failed!");
            return Response.ERROR("启动失败");
        }
        return Response.SUCCESS("ok");
    }

    @RequestMapping("list")
    public Response list(Integer page, Integer size){
        return Response.SUCCESS(jobService.list(page,size));
    }

    @RequestMapping("submit")
    public Response submit(@RequestBody Job job){

        return Response.SUCCESS(jobService.list(1,20));
    }

}
