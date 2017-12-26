package com.xingoo.streaming.monitor.service;

import com.xingoo.streaming.monitor.dao.TaskJPARepository;
import com.xingoo.streaming.monitor.manager.job.ProcessManager;
import com.xingoo.streaming.monitor.manager.job.Task;
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

    public void saveAndStart(Task task){
        taskJPARepository.save(task);
        processManager.start(task);
    }

    public List<Task> listAll(){
        return taskJPARepository.findAll();
    }
}