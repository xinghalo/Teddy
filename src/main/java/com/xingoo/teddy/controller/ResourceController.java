package com.xingoo.teddy.controller;

import com.xingoo.teddy.manager.ResourceManager;
import com.xingoo.teddy.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("jar")
public class ResourceController {

    @Autowired
    private ResourceManager resourceManager;

    @RequestMapping("upload")
    public Response upload(@RequestParam("file") MultipartFile file){
        return Response.SUCCESS(resourceManager.save(file));
    }

    @RequestMapping("list")
    public Response list(){
        return Response.SUCCESS(resourceManager.listJars());
    }

    @RequestMapping("delete")
    public Response delete(String jar){
        return Response.SUCCESS(resourceManager.delete(jar));
    }
}
