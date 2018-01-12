package com.xingoo.teddy.service;

import com.xingoo.teddy.manager.ProcessManager;
import com.xingoo.teddy.manager.Task;
import com.xingoo.teddy.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private ProcessManager processManager;

    public Task findOne(String id) {
//        return jobMapper.findOne(id);
        return null;
    }

    public void save(Task task){
//        jobMapper.save(task);
    }

    public List<Task> start(Task task){
        processManager.start(task);
        return listAll();
    }

    public List<Task> listAll(){ ;
//        return jobMapper.findAll();
        return null;
    }

    public List<Task> delete(String id){
        jobMapper.delete(id);
//        return jobMapper.findAll();
        return null;
    }

    public List<Task> stop(String id) {
//        Task task = jobMapper.findOne(id);
//        processManager.stop(task);
//        return jobMapper.findAll();
        return null;
    }

    public List<Task> findAllByApplicationId(){
//        return jobMapper.findAllByApplicationId();
        return null;
    }

    public void updateStateById(String id, String state, Date modifyTime){
        jobMapper.updateStateById(id,state,modifyTime);
    }

    public void update(String id, String state, Date modifyTime, String applicationId){
        jobMapper.update(id,state,modifyTime,applicationId);
    }
}