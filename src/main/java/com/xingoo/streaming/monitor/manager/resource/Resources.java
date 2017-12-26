package com.xingoo.streaming.monitor.manager.resource;

import com.xingoo.streaming.monitor.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.util.Collection;

public class Resources {

    /**
     * 显示所有的jars
     * @return file数组
     */
    public static Collection<File> listJars(){

        return FileUtils.listFiles(new File(Constants.STORAGE_PATH),
                FileFilterUtils.suffixFileFilter("jar"),
                null);
    }

}
