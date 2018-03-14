package com.xingoo.teddy.service;

import com.xingoo.teddy.entity.Job;
import com.xingoo.teddy.mapper.JobMapper;
import com.xingoo.teddy.utils.TeddyConf;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobMapper jobMapper;

    public SparkAppHandle start(Job job) throws IOException {

        SparkLauncher launcher = new SparkLauncher()
                .setAppName(job.getName())
                .setSparkHome(TeddyConf.get("spark.home"))
                .setMaster(job.getMaster())
                .setAppResource(job.getApp_resource())
                .setMainClass(job.getMain_class())
                .addAppArgs(job.getArgs())
                .setDeployMode(job.getDeploy_mode());

        String settings = job.getConfig();
        for(String setting : StringUtils.splitByWholeSeparator(settings,";")){
            String[] strings = StringUtils.split(setting, "=");
            launcher.setConf(strings[0],strings[1]);
        }

        SparkAppHandle handler = launcher.startApplication();

        // 阻塞登到有id再返回
        while(handler.getAppId()==null){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return handler;
    }

    /**
     * 创建task表
     */
    public void create(){
        jobMapper.create();
    }

    /**
     * 查询表的个数，用于判断表是否存在
     * @return 如果表不存在则为-1
     */
    public Integer count() {
        try {
            return jobMapper.count();
        } catch (Exception e) {
            return -1;
        }
    }

    public List<Job> list(Integer page, Integer size){
        return jobMapper.list((page-1)*size,size);
    }

    public void save(Job job){
        jobMapper.save(job);
    }



}
