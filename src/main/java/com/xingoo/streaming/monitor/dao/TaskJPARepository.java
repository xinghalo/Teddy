package com.xingoo.streaming.monitor.dao;

import com.xingoo.streaming.monitor.manager.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJPARepository extends JpaRepository<Task,String> {
}
