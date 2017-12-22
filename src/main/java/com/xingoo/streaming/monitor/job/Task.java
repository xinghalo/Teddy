package com.xingoo.streaming.monitor.job;

import com.xingoo.streaming.monitor.resource.Resources;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 任务类的封装
 *
 * @author xingoo
 */
public class Task {

    private String name;        //job的名字
    private String clazz;       //启动的class
    private String jar;         //启动的jar
    private String settings = " --master yarn --deploy-mode cluster --executor-memory 5G --num-executors 5 --executor-cores 3 --driver-memory 5G ";    //spark运行配置
    private Long createTime = Timestamp.valueOf(LocalDateTime.now()).getTime();    //创建时间

    public Task(){}

    public Task(String name,String clazz,String jar){
        this.name = name;
        this.clazz = clazz;
        this.jar = jar;
    }

    public Task(String name,String clazz,String jar,String settings){
        this(name,clazz,jar);
        this.settings = settings;
    }

    public Task setName(String name){
        this.name = name;
        return this;
    }

    public Task setClazz(String clazz){
        this.clazz = clazz;
        return this;
    }

    public Task setJar(String jar){
        this.jar = jar;
        return this;
    }

    public Task setSettings(String settings){
        this.settings = settings;
        return this;
    }

    public String getId(){
        return this.name+"_"+this.createTime;
    }

    /**
     * 获得启动命令
     * @return 命令
     */
    public String getCommand(){

        String jars = String.join(",",Arrays
                .stream(Resources.listJars())
                .filter(file -> !file.getName().equals(jar)) // 排除本身要启动的jar
                .map(File::getName)
                .collect(Collectors.toList()));

        StringBuilder command = new StringBuilder();
        command.append("spark2-submit ");
        command.append(settings);
        command.append(" --jars \"");
        command.append(jars);
        command.append("\"");
        command.append(" --class ");
        command.append(clazz);
        command.append(jar);

        return command.toString();
    }
}
