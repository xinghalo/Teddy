package com.xingoo.streaming.monitor.controller;

import com.xingoo.streaming.monitor.manager.resource.Resources;
import com.xingoo.streaming.monitor.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("jar")
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @RequestMapping("upload")
    public Collection<File> upload(@RequestParam("file") MultipartFile file){
        try {
            FileUtils.writeByteArrayToFile(
                    new File(Constants.STORAGE_PATH+file.getOriginalFilename()),
                    file.getBytes());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return list();
    }

    @RequestMapping("list")
    public Collection<File> list(){
        return Resources.listJars();
    }

}
