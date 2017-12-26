package com.xingoo.streaming.monitor.manager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceManager {

    public static final String PATH = "com.xingoo.streaming.monitor.resource.path";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    /**
     * 显示所有的jars
     * @return file数组
     */
    public List<String> listJars(){

        Collection<File> files = FileUtils.listFiles(new File(env.getProperty(PATH)),
                FileFilterUtils.suffixFileFilter("jar"),
                null);
        return files.stream().map(file -> file.getName()).collect(Collectors.toList());

    }

    /**
     * 保存到指定的目录
     * @param file
     * @return
     */
    public List<String> save(MultipartFile file){
        try {
            FileUtils.writeByteArrayToFile(new File(env.getProperty(PATH)+file.getOriginalFilename()),
                    file.getBytes());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return listJars();
    }

}
