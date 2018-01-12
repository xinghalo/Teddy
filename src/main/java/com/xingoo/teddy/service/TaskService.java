package com.xingoo.teddy.service;

import com.xingoo.teddy.manager.ProcessManager;
import com.xingoo.teddy.entity.Task;
import com.xingoo.teddy.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskMapper mapper;

    @Autowired
    private ProcessManager processManager;

    /**
     * 创建task表
     */
    public void create(){
        mapper.create();
    }

    /**
     * 查询表的个数，用于判断表是否存在
     * @return 如果表不存在则为-1
     */
    public Integer count() {
        try {
            return mapper.count();
        } catch (Exception e) {
            return -1;
        }
    }

    public Task findOne(String id) {
        return mapper.findOne(id);
    }

    public void save(Task task){
        mapper.save(task);
    }

    public List<Task> start(Task task){
        processManager.start(task);
        return listAll();
    }

    public List<Task> listAll(){ ;
        return mapper.findAll();
    }

    public List<Task> delete(String id){
        mapper.delete(id);
        return mapper.findAll();
    }

    public List<Task> stop(String id) {
        Task task = mapper.findOne(id);
        processManager.stop(task);
        return mapper.findAll();
    }

    public List<Task> findAllByApplicationId(){
        return mapper.findAllByApplicationId();
    }

    public void updateStateById(String id, String state, Date modifyTime,String application_id){
        mapper.updateStateById(id,state,modifyTime,application_id);
    }

    public void update(String id, String state, Date modifyTime, String applicationId){
        mapper.update(id,state,modifyTime,applicationId);
    }

    public void register(String taskId,String appId, String state, String url){
        mapper.register(taskId,appId,state,url,new Date(System.currentTimeMillis()));
    }


    public void restart(String id,Integer count) throws Exception{
        mapper.restart(id,count);
    }
}