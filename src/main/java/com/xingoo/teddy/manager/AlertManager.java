package com.xingoo.teddy.manager;

import com.alibaba.fastjson.JSON;
import com.xingoo.teddy.entity.Job;
import com.xingoo.teddy.service.JobService;
import com.xingoo.teddy.utils.TeddyConf;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class AlertManager implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private JobService jobService;

    private ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("alert-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 告警服务 线程");

        scheduledThreadPool.scheduleAtFixedRate(()->{
            try {
                List<Job> jobs = jobService.findAllWithAppId();
                jobs.forEach(t -> {
                    if (StringUtils.isNotBlank(t.getState())
                            && !"RUNNING".equals(t.getState())
                            && t.getSend() == 1) {
                        logger.error("检测到异常任务");
                        emailSender.send(t.getEmail(), t.getName() + "状态异常", JSON.toJSONString(t));

                    }
                });
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        },0, Long.valueOf(TeddyConf.get("alert.interval")), TimeUnit.SECONDS);
    }
}
