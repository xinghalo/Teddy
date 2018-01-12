package com.xingoo.teddy.controller;

import com.xingoo.teddy.entity.Task;
import com.xingoo.teddy.manager.ProcessManager;
import com.xingoo.teddy.manager.ResourceManager;
import com.xingoo.teddy.service.TaskService;
import com.xingoo.teddy.service.YarnService;
import com.xingoo.teddy.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("task")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${register.url}")
    private String register;

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private TaskService taskService;

    @Autowired
    private YarnService yarnService;

    @RequestMapping("start")
    public Response start(String name,
                          String clazz,
                          String jar,
                          String settings,
                          String email,
                          Integer send,
                          Integer restart,
                          String args){



        // 封装命令
        Task task = new Task(
                name,
                clazz,
                resourceManager.getJar(jar),
                resourceManager.getCommandJars(jar),
                settings,
                args+" "+register,
                email,
                send,
                restart
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

    @RequestMapping("update")
    public void update(String taskId,String appId,String state){
        taskService.update(taskId,state,new Date(System.currentTimeMillis()),appId);
    }

    @RequestMapping("register")
    public Response register(String taskId,String appId,String state,String url){
        taskService.register(taskId,appId,state,url);
        return Response.SUCCESS(null);
    }
}
