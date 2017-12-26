package com.xingoo.streaming.monitor.dao;

import com.xingoo.streaming.monitor.manager.job.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TaskJPARepository extends JpaRepository<Task,String> {
}
