package com.xingoo.streaming.monitor.manager;

import com.xingoo.streaming.monitor.dao.TaskJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlertManager implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${alert.interval}")
    private Integer alertInterval;

    @Autowired
    private TaskJPARepository taskJPARepository;

    @Autowired
    private EmailSender emailSender;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 告警服务 线程");

        new Thread(()->{
            while(true) {
                List<Task> task = taskJPARepository.findAll();
                task.forEach(t -> {
                    if(t.getState()!=null && !"RUNNING".equals(t.getState()) && t.getIs_send_email()==1){
                        emailSender.send(t.getEmail(),t.getName()+"状态异常",t.getCommand()+"<br>"+t.getWeb_url());
                    }
                });

                try {
                    Thread.sleep(alertInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
