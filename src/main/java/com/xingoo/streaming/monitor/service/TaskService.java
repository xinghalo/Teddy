package com.xingoo.streaming.monitor.service;

import com.xingoo.streaming.monitor.dao.TaskJPARepository;
import com.xingoo.streaming.monitor.manager.ProcessManager;
import com.xingoo.streaming.monitor.manager.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskJPARepository taskJPARepository;

    @Autowired
    private ProcessManager processManager;

    public Task findOne(String id) {
        return taskJPARepository.findOne(id);
    }

    public void save(Task task){
        taskJPARepository.save(task);
    }

    public List<Task> start(Task task){
        processManager.start(task);
        return listAll();
    }

    public List<Task> listAll(){
        return taskJPARepository.findAll();
    }

    public List<Task> delete(String id){
        taskJPARepository.delete(id);
        return taskJPARepository.findAll();
    }

    public List<Task> stop(String id) {
        Task task = taskJPARepository.findOne(id);
        processManager.stop(task);
        return listAll();
    }
}