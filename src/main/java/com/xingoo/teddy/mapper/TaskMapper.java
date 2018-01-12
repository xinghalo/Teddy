package com.xingoo.teddy.mapper;

import com.xingoo.teddy.manager.Task;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("select count(1) from task")
    Integer count() throws Exception;

    @Update({"create table task(" +
            "id VARCHAR(100)," +
            "name VARCHAR(200)," +
            "command VARCHAR(3000)," +
            "create_time BIGINT," +
            "application_id VARCHAR(200)," +
            "web_url VARCHAR(200)," +
            "state VARCHAR(20)," +
            "email VARCHAR(500)," +
            "send SMALLINT," +
            "modify_time TIMESTAMP," +
            "primary key (id))"})
    void create();

    @Update("update task t set t.state = #{state}, t.modify_time = #{now} where t.id = #{id}")
    void updateStateById(@Param("id")    String id,
                         @Param("state") String state,
                         @Param("now")   Date now);

    @Select("select * from task where application_id is not null")
    List<Task> findAllByApplicationId();

    @Select("select * from task where id = #{id}")
    Task findOne(@Param("id")String id);

    @Insert({"insert into task(id,name,command,create_time,email,send,modify_time) " +
            "values(#{t.id}, #{t.name}, #{t.command},#{t.create_time}, #{t.email}, #{t.send},#{t.modify_time})"})
    void save(@Param("t") Task task);

    @Select("select * from task")
    List<Task> findAll();

    @Delete("delete from task where id = #{id}")
    void delete(@Param("id") String id);

    @Update("update task set state=#{state}, modify_time=#{mt}, application_id=#{appid} where id = #{id}")
    void update(@Param("id")    String  id,
                @Param("state") String  state,
                @Param("mt")    Date    modifyTime,
                @Param("appid") String  applicationId);
}
