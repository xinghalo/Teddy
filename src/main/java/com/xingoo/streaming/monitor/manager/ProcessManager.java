package com.xingoo.streaming.monitor.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 如果考虑单例，可以参考http://cantellow.iteye.com/blog/838473
 *
 */
@Service
public class ProcessManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void start(Task task){
        new Thread(()->{
            try {
                System.out.println(task.getCommand());

                Process process = Runtime.getRuntime().exec(task.getCommand());

//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                BufferedReader readerError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(Thread.currentThread().getName()+":"+line);
//                }
            } catch (IOException e) {

                logger.error(e.getMessage());
            }
        }).start();
    }

}
