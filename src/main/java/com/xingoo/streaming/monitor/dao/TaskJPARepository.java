package com.xingoo.streaming.monitor.dao;

import com.xingoo.streaming.monitor.manager.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public interface TaskJPARepository extends JpaRepository<Task,String> {

    @Query("select t from Task t where t.application_id is not null")
    List<Task> findAllByApplicationId();

    @Transactional
    @Modifying
    @Query("update Task t set t.state = ?2, t.modify_time = ?3 where t.id = ?1")
    int updateStateById(String id, String state, Date now);
}
