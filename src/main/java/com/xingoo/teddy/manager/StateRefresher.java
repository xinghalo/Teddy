package com.xingoo.teddy.manager;

import com.xingoo.teddy.entity.Task;
import com.xingoo.teddy.service.TaskService;
import com.xingoo.teddy.service.YarnService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class StateRefresher implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${state.refresh.interval}")
    private Integer stateRefreshInterval;

    @Autowired
    private YarnService yarnService;

    @Autowired
    private TaskService taskService;

    private ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("state-refresher-pool-%d").daemon(true).build());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 状态服务 线程");

        // 线程池代替单个线程的创建，提供更好的复用性与可控性
        scheduledThreadPool.scheduleAtFixedRate(()->{
            List<Task> task = taskService.findAllByApplicationId();
            logger.info("监测到" + task.size() + "条appid不为空的task");
            task.forEach(t -> {
                String state = yarnService.state(t.getApplication_id());
                taskService.updateStateById(t.getId(), state, new Date(System.currentTimeMillis()),t.getApplication_id());
                logger.info("更新" + t.getId() + "的状态信息为：" + state);
            });
        },0,stateRefreshInterval, TimeUnit.SECONDS);
    }
}
