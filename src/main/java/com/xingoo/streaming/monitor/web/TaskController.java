package com.xingoo.streaming.monitor.web;

import com.xingoo.streaming.monitor.job.Task;
import com.xingoo.streaming.monitor.job.ProcessManager;
import com.xingoo.streaming.monitor.utils.Constants;
import com.xingoo.streaming.monitor.utils.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private Environment env;

    @RequestMapping("start")
    public Response start(String name, String clazz, String jar, String settings){
        if(StringUtils.isBlank(jar) || StringUtils.isBlank(clazz)){
            return Response.ERROR("参数不合法");
        }

        // 封装命令
        Task task = new Task(name,clazz,jar,settings);

        // 启动进程
        return Response.SUCCESS(processManager.start(task));
    }

    @RequestMapping("stop")
    public Response stop(String id){
        if(processManager.stop(id)){
            return Response.SUCCESS(processManager.list());
        }
        return Response.ERROR("失败");
    }

    @RequestMapping("list")
    public Response list(){
        return Response.SUCCESS(processManager.list());
    }
}
