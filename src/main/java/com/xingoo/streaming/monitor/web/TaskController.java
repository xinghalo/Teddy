package com.xingoo.streaming.monitor.web;

import com.xingoo.streaming.monitor.job.Task;
import com.xingoo.streaming.monitor.job.ProcessManager;
import com.xingoo.streaming.monitor.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private Environment env;

    @RequestMapping("start")
    public String start(String name, String clazz, String jar, String settings){
        if(StringUtils.isBlank(jar) || StringUtils.isBlank(clazz)){
            return "error";
        }

        // 封装命令
        Task task = new Task(name,clazz,jar,settings);

        // 启动进程
        return processManager.start(task);
    }

    @RequestMapping("stop")
    public String stop(String id){

        return "success";
    }

    @RequestMapping("list")
    public String list(){
        return "success";
    }
}
