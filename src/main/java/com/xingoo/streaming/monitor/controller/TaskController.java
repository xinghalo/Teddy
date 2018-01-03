package com.xingoo.streaming.monitor.controller;

import com.xingoo.streaming.monitor.manager.ProcessManager;
import com.xingoo.streaming.monitor.manager.ResourceManager;
import com.xingoo.streaming.monitor.manager.StreamingConfig;
import com.xingoo.streaming.monitor.manager.Task;
import com.xingoo.streaming.monitor.service.TaskService;
import com.xingoo.streaming.monitor.service.YarnService;
import com.xingoo.streaming.monitor.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private TaskService taskService;

    @Autowired
    private YarnService yarnService;

    @Autowired
    private StreamingConfig config;

    @RequestMapping("start")
    public Response start(String name,
                          String clazz,
                          String jar,
                          String settings,
                          String email,
                          Integer is_send_email,
                          String[] args){



        // 封装命令
        Task task = new Task(
                name,
                clazz,
                resourceManager.getJar(jar),
                resourceManager.getCommandJars(jar),
                settings,
                args,
                email,
                is_send_email,
                config
        );

        // 保存并启动进程
        taskService.save(task);
        taskService.start(task);

        return Response.SUCCESS(taskService.listAll());
    }

    @RequestMapping("delete")
    public Response delete(String id){
        Task task = taskService.findOne(id);
        if("RUNNING".equals(task.getState())){
            return Response.ERROR("无法删除正在运行的任务");
        }else{
            return Response.SUCCESS(taskService.delete(id));
        }
    }

    @RequestMapping("stop")
    public Response stop(String id){
        return Response.SUCCESS(taskService.stop(id));
    }

    @RequestMapping("list")
    public Response list(){
        return Response.SUCCESS(taskService.listAll());
    }

    @RequestMapping("restart")
    public Response restart(String id){
        Task task = taskService.findOne(id);
        if("RUNNING".equals(task.getState())){
            return Response.ERROR("正在执行，无法重启");
        }else{
            return Response.SUCCESS(taskService.start(task));
        }
    }

    @RequestMapping("find")
    public Response find(String id){
        return Response.SUCCESS(taskService.findOne(id));
    }
}
