package com.xingoo.streaming.monitor.web;

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

@RestController
@RequestMapping("jar")
public class ResourceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @RequestMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file){
        String resourcePath = env.getProperty(Constants.STORAGE_PATH);

        try {
            FileUtils.writeByteArrayToFile(
                    new File(resourcePath+file.getOriginalFilename()),
                    file.getBytes());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return resourcePath;
    }

}
