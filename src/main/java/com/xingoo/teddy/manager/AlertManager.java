package com.xingoo.teddy.manager;

import com.xingoo.teddy.entity.Task;
import com.xingoo.teddy.service.TaskService;
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

    @Value("${alert.interval}")
    private Integer alertInterval;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private TaskService taskService;

    private ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("alert-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 告警服务 线程");

        scheduledThreadPool.scheduleAtFixedRate(()->{
            try {
                List<Task> tasks = taskService.listAll();
                tasks.forEach(t -> {
                    if (StringUtils.isNotBlank(t.getState())
                            && !"RUNNING".equals(t.getState())
                            && t.getSend() == 1) {

                        emailSender.send(t.getEmail(), t.getName() + "状态异常", t.getCommand() + "<br>" + t.getWeb_url());

                    }
                });
            }catch(Exception e){
                logger.error(e.getMessage());
            }
        },0,alertInterval, TimeUnit.SECONDS);
    }
}
