package com.xingoo.teddy.manager;

import com.xingoo.teddy.entity.Task;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SparkLauncherManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void start(Task task) {
        try {
            SparkAppHandle handler =  new SparkLauncher().setAppName("test").setMaster("local*").startApplication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
