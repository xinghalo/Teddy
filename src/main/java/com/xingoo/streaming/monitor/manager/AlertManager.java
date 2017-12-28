package com.xingoo.streaming.monitor.manager;

import com.xingoo.streaming.monitor.dao.TaskJPARepository;
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

    @Value("${alert.interval}")
    private Integer alertInterval;

    @Autowired
    private TaskJPARepository taskJPARepository;

    @Autowired
    private EmailSender emailSender;

    private ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("alert-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 告警服务 线程");

        scheduledThreadPool.scheduleAtFixedRate(()->{
            List<Task> tasks = taskJPARepository.findAll();
            tasks.forEach(t -> {
                if(t.getState()!=null && !"RUNNING".equals(t.getState()) && t.getIs_send_email()==1){
                    emailSender.send(t.getEmail(),t.getName()+"状态异常",t.getCommand()+"<br>"+t.getWeb_url());
                }
            });
        },0,alertInterval, TimeUnit.SECONDS);
    }
}
