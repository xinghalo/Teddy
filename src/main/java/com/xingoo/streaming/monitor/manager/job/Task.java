package com.xingoo.streaming.monitor.manager.job;

import com.xingoo.streaming.monitor.manager.resource.Resources;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 任务类的封装
 *
 * @author xingoo
 */
@Entity
@Table(name="streaming_task")
public class Task implements Serializable{

    private String id;
    private String name;        //job的名字
    private String command;
    private Long create_time = Timestamp.valueOf(LocalDateTime.now()).getTime();    //创建时间
    private String application_id;
    private String web_url;
    private String state;
    private Date modify_time;

    public Task(){}

    public Task(String id,String name,String command,Long create_time){
        this.id = id;
        this.name = name;
        this.command = command;
        this.create_time = create_time;
    }

    public Task(String name,String clazz,String jar,String settings, String[] args){
        this.name = name;
        this.id = this.name+"_"+this.create_time;

        String jars = StringUtils.join(Resources.listJars().stream().map(File::getName).toArray(),",");

        this.command = "spark2-submit "
                + settings
                + " --jars \""
                + jars
                + "\" --class "
                + clazz
                + " "
                + jar
                + " "
                + String.join(" ",args)
                + " "
                + id;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }
}
