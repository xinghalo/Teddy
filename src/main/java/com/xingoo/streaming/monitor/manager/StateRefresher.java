package com.xingoo.streaming.monitor.manager;

import com.alibaba.fastjson.JSON;
import com.xingoo.streaming.monitor.dao.TaskJPARepository;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class StateRefresher implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskJPARepository taskJPARepository;

    //http://hc.apache.org/httpcomponents-client-4.5.x/quickstart.html
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("启动 状态服务 线程");

        new Thread(()->{
            while(true) {
                List<Task> task = taskJPARepository.findAllByApplicationId();
                logger.info("监测到" + task.size() + "条appid不为空的task");
                task.forEach(t -> {
                    try {
                        CloseableHttpClient httpclient = HttpClients.createDefault();
                        //https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/ResourceManagerRest.html#Cluster_Application_State_API
                        HttpGet httpGet = new HttpGet("http://hnode2:8088/ws/v1/cluster/apps/" + t.getApplication_id() + "/state");
                        httpGet.setHeader("Content-Type", "application/json");
                        CloseableHttpResponse resp = httpclient.execute(httpGet);

                        // 解析结果，并更新
                        if (resp.getStatusLine().getStatusCode() == 200) {
                            String content = IOUtils.toString(resp.getEntity().getContent());
                            String state = JSON.parseObject(content).getString("state");
                            taskJPARepository.updateStateById(t.getId(), state, new Date(System.currentTimeMillis()));
                            logger.info("更新" + t.getId() + "的状态信息为：" + state);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
