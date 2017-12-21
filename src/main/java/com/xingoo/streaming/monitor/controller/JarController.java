package com.xingoo.streaming.monitor.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("jar")
public class JarController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file){
        try {
            FileUtils.writeByteArrayToFile(
                    new File("/Users/xingoo/uploads/"+file.getOriginalFilename()),
                    file.getBytes());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return "success";
    }

}
