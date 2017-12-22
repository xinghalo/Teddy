package com.xingoo.streaming.monitor.job;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
    private static BlockingQueue<Process> processes = new ArrayBlockingQueue<Process>(1000);

    /**
     * 基于线程池创建spark streaming启动任务
     */
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String start(String id, String command, String path){
        pool.execute(new Thread(()->{
            FileWriter writer = null;
            try {
                writer = new FileWriter("/Users/xingoo/uploads/out1", true);
//                File log = new File("/User/xingoo/uploads/1");
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    //FileUtils.write(log,line,true);
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
        return "aaa";
    }
}
