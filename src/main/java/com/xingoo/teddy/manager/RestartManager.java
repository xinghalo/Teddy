package com.xingoo.teddy.manager;

import com.alibaba.fastjson.JSON;
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
public class RestartManager implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${auto.restart.interval}")
    private Integer autoRestartInterval;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessManager processManager;

    private ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("restart-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("start restarter");

        scheduledThreadPool.scheduleAtFixedRate(()->{
            try {
                List<Task> tasks = taskService.listAll();
                logger.info("扫描到" + tasks.size() + "个任务需要检测是否重启");
                tasks.forEach(t -> {
                    if (StringUtils.isNotBlank(t.getState())
                            && !"RUNNING".equals(t.getState())
                            && t.getRestart().equals(1)
                            && t.getRestart_count() > 0) {
                        try {
                            logger.info("尝试重启task:" + t.getId() + ",剩余次数:" + t.getRestart_count());
                            taskService.restart(t.getId(), t.getRestart_count() - 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        processManager.start(t);
                    }
                });
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        },0,autoRestartInterval, TimeUnit.SECONDS);
    }
}
