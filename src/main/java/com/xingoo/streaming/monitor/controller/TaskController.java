package com.xingoo.streaming.monitor.controller;

import com.xingoo.streaming.monitor.manager.job.ProcessManager;
import com.xingoo.streaming.monitor.manager.job.Task;
import com.xingoo.streaming.monitor.service.TaskService;
import com.xingoo.streaming.monitor.utils.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private TaskService taskService;

    @RequestMapping("start")
    public Response start(String name, String clazz, String jar, String settings, String[] args){
        if(StringUtils.isBlank(jar) || StringUtils.isBlank(clazz)){
            return Response.ERROR("参数不合法");
        }

        // 封装命令
        Task task = new Task(name,clazz,jar,settings,args);

        // 保存并启动进程
        taskService.saveAndStart(task);

        return Response.SUCCESS(taskService.listAll());
    }

    @RequestMapping("stop")
    public Response stop(String id){
//        return Response.ERROR("失败");
        return Response.SUCCESS(null);
    }

    @RequestMapping("list")
    public Response list(){
        return Response.SUCCESS(taskService.listAll());
    }
}
