package com.xingoo.streaming.monitor.job;

import com.xingoo.streaming.monitor.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 如果考虑单例，可以参考http://cantellow.iteye.com/blog/838473
 *
 */
@Service
public class ProcessManager {

    /**
     * 使用blockingQueue保存process对象
     *
     * http://blog.csdn.net/suifeng3051/article/details/48807423
     */
    private static ConcurrentHashMap<String,Task> processes = new ConcurrentHashMap<>();

    /**
     * 基于线程池创建spark streaming启动任务
     */
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Task start(Task task){
        pool.execute(new Thread(()->{
            FileWriter writer = null;
            try {
                writer = new FileWriter(Constants.LOG_PATH+task.getId(), true);

                Process process = Runtime.getRuntime().exec("ping www.baidu.com");
                processes.put(task.getId(),task.setProcess(process));
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                // todo 这么输出真古老！！！感觉像是jdk 1.5的代码！
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(Thread.currentThread().getName()+":"+line);
                    writer.write(line);
                    writer.flush();
                }

            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));

        return task;
    }

    public Boolean stop(String id){
        processes.get(id).getProcess().destroy();
        processes.remove(id);
        return true;
    }

    public Collection<Task> list(){
        return processes.values();
    }
}
