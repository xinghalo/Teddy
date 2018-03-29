package com.xingoo.teddy.manager;

import com.xingoo.teddy.utils.TeddyConf;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    public List<String> listJars(){

        File directory = new File(TeddyConf.get("lib.home"));
        IOFileFilter filter = FileFilterUtils.suffixFileFilter("jar");

        // filter *.jar
        Collection<File> files = FileUtils.listFiles(directory,filter,null);
        return files.stream().map(File::getAbsolutePath).collect(Collectors.toList());

    }

    public List<String> save(MultipartFile file){
        try {
            File saveFile = new File(TeddyConf.get("lib.home")+file.getOriginalFilename());

            // delete when exsit
            if(saveFile.exists()){
                saveFile.delete();
            }

            // save
            FileUtils.writeByteArrayToFile(saveFile,file.getBytes());

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return listJars();
    }

    public String getCommandJars(String jar){

        List<String> jars = listJars().stream()
                .filter(s -> !s.equals(getJar(jar)))
                .collect(Collectors.toList());

        return StringUtils.join(jars, ",");
    }

    public String getJar(String jar){
        return TeddyConf.get("lib.home")+jar;
    }

    public List<String> delete(String jar){
        File file = new File(jar);
        if(file.exists()){
            file.delete();
        }
        return listJars();
    }

}
