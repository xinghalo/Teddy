package com.xingoo.streaming.monitor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
public class JobController {

    @RequestMapping("start")
    public String start(String clazz){

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
