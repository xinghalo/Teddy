package com.xingoo.streaming.monitor.manager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class StreamingConfig {

    @Value("${streaming.config.default_web_ui}")
    private String defaultWebUI;

    @Value("${streaming.config.class_name}")
    private String className;

    @Value("${streaming.config.jdbc_url}")
    private String jdbcURL;

    @Value("${streaming.config.jdbc_user}")
    private String jdbcUSER;

    @Value("${streaming.config.jdbc_passwd}")
    private String jdbcPASSWD;

    public String generateArgs(String appName,String taskId,String[] args){
        List<String> totalArgs = new ArrayList<>();
        totalArgs.add(appName);
        totalArgs.add(taskId);
        totalArgs.add(defaultWebUI);
        totalArgs.add(className);
        totalArgs.add(jdbcURL);
        totalArgs.add(jdbcUSER);
        totalArgs.add(jdbcPASSWD);
        totalArgs.addAll(Arrays.asList(args));
        return StringUtils.join(totalArgs," ");
    }
}
