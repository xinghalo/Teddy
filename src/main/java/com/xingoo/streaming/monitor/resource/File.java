package com.xingoo.streaming.monitor.resource;

public class File {
    private String name;
    private String size;
    private String createTime;

    public File(String name, String size, String createTime){
        this.name = name;
        this.size = size;
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
