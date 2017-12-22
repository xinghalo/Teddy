package com.xingoo.streaming.monitor.web;

import com.xingoo.streaming.monitor.job.ProcessManager;
import com.xingoo.streaming.monitor.resource.ResourceManager;
import com.xingoo.streaming.monitor.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("job")
public class JobController {

    @Autowired
    private ResourceManager resourceManager;

    @Autowired
    private ProcessManager processManager;

    @Autowired
    private Environment env;

    @RequestMapping("start")
    public String start(String jar, String clazz, String settings, String name){
        if(StringUtils.isBlank(settings)){
            settings = " --master yarn --deploy-mode cluster --executor-memory 5G --num-executors 5 --executor-cores 3 --driver-memory 5G ";
        }

        if(StringUtils.isBlank(jar) || StringUtils.isBlank(clazz)){
            return "error";
        }

        String resourcePath = env.getProperty(Constants.STORAGE_PATH);

        // 封装命令
        StringBuilder command = new StringBuilder();
        command.append(" spark2-submit ");
        command.append(settings);
        command.append(" --jars \"");
        command.append(resourceManager.addJars(name));
        command.append("\"");
        command.append(" --class ");
        command.append(clazz);
        command.append(name);
//            command.append(" --name ");
//            command.append(name.split(".jar")[0]);

        // 启动进程
        processManager.start(name,"ping www.baidu.com",resourcePath);

        return "success";
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
