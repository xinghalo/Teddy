package com.xingoo.teddy.entity;

public class Job {
    private Integer id;
    private String name;
    private String app_id = "-1";
    private String state = "created";
    private String app_resource;
    private String main_class;
    private String master = "yarn";
    private String deploy_mode = "cluster";
    private String args;
    private String config;
    private String email;
    private Integer send;
    private Integer restart;
    private Integer retries = 3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApp_resource() {
        return app_resource;
    }

    public void setApp_resource(String app_resource) {
        this.app_resource = app_resource;
    }

    public String getMain_class() {
        return main_class;
    }

    public void setMain_class(String main_class) {
        this.main_class = main_class;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getDeploy_mode() {
        return deploy_mode;
    }

    public void setDeploy_mode(String deploy_mode) {
        this.deploy_mode = deploy_mode;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    public Integer getRestart() {
        return restart;
    }

    public void setRestart(Integer restart) {
        this.restart = restart;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }
}
