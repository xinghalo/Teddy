package com.xingoo.streaming.monitor.resource;

import com.xingoo.streaming.monitor.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.hadoop.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceManager {

    @Autowired
    private Environment env;

    public String addJars(String name){

        List<String> names = Arrays
                .stream(listJars())
                .filter(file -> !file.getName().equals(name)) // 排除本身要启动的jar
                .map(File::getName)
                .collect(Collectors.toList());

        return StringUtils.join(",",names);
    }

    public File[] listJars(){

        String resourcePath = env.getProperty(Constants.STORAGE_PATH);

        return FileUtils.listFiles(new File(resourcePath),
                FileFilterUtils.suffixFileFilter("jar"),
                null)
                .toArray(new File[]{});
    }
}
