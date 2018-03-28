package com.xingoo.teddy.service;

import com.xingoo.teddy.entity.Job;
import com.xingoo.teddy.mapper.JobMapper;
import com.xingoo.teddy.utils.TeddyConf;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Service
public class JobService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobMapper jobMapper;

    public Boolean start(Job job){
        SparkAppHandle handler = null;
        try {
            handler = launch(job);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
        job.setApp_id(handler.getAppId());
        save(job);
        return true;
    }

    private SparkAppHandle launch(Job job)throws IOException {

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

    public Boolean restart(Job job, Integer retries){
        SparkAppHandle handler = null;
        try {
            handler = launch(job);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }

        if(retries!=null) {
            updateAppIdById(job.getId(), handler.getAppId(), retries);
        }else{
            updateAppIdById(job.getId(), handler.getAppId());
        }
        return true;
    }

    public Boolean stop(Job job) {
        String command = "yarn application -kill "+job.getApp_id();
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        logger.info("kill "+job.getApp_id());
        return true;
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

    public List<Job> findAllWithAppId(){
        return jobMapper.findAllWithAppId();
    }

    public void save(Job job){
        jobMapper.save(job);
    }

    public Job findOne(Integer id){
        return jobMapper.findOne(id);
    }

    public void delete(Integer id){
        jobMapper.delete(id);
    }

    public Boolean stop(Integer id){
        Job job = jobMapper.findOne(id);
        return stop(job);
    }

    public void updateStateById(Integer id, String state){
        jobMapper.updateStateById(id, state, new Date(System.currentTimeMillis()));
    }

    public void updateAppIdById(Integer id, String appId, Integer retries){
        jobMapper.updateAppIdById(id, appId, retries, new Date(System.currentTimeMillis()));
    }

    public void updateAppIdById(Integer id, String appId){
        jobMapper.updateAppIdById(id, appId, new Date(System.currentTimeMillis()));
    }

}
