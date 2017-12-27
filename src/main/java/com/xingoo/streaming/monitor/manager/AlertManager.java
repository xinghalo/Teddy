package com.xingoo.streaming.monitor.manager;

import com.xingoo.streaming.monitor.dao.TaskJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlertManager implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskJPARepository taskJPARepository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 状态服务 线程");

        new Thread(()->{
            while(true) {
                List<Task> task = taskJPARepository.findAll();
                task.forEach(t -> {
                    if(!t.getState().equals("RUNNING") && t.getIs_send_email()==1){
                        logger.info("假装给"+t.getEmail()+"发了一封邮件");
                    }
                });

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
