package com.xingoo.teddy.mapper;

import com.xingoo.teddy.entity.Job;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface JobMapper {

    @Select("select count(1) from job")
    Integer count() throws Exception;

    /**
     * ALWAYS 不允许手动指定 ID
     * DEFAULT 也可以手动指定ID
     */
    @Update("create table job(" +
            "id                 BIGINT GENERATED ALWAYS AS IDENTITY," +
            "name               VARCHAR(200)," +
            "app_id             VARCHAR(200)," +
            "state              VARCHAR(20)," +
            "app_resource       VARCHAR(200)," +
            "main_class         VARCHAR(200)," +
            "master             VARCHAR(20)," +
            "deploy_mode        VARCHAR(20)," +
            "args               VARCHAR(3000)," +
            "config             VARCHAR(3000)," +
            "email              VARCHAR(200)," +
            "send               SMALLINT," +
            "restart            SMALLINT," +
            "retries            SMALLINT," +
            "modify_time        TIMESTAMP)")
    void create();

    @Select("select * from job OFFSET #{start} ROWS FETCH NEXT #{size} ROWS ONLY")
    List<Job> list(@Param("start")Integer start, @Param("size")Integer size);

    @Select("select * from job where app_id != '-1'")
    List<Job> findAllWithAppId();

    @Insert({"insert into job(" +
            "name,app_id,state,app_resource,main_class,master,deploy_mode,args,config,email,send,restart,retries" +
            ") values(" +
            "#{t.name}, #{t.app_id}, #{t.state},#{t.app_resource}, " +
            "#{t.main_class}, #{t.master},#{t.deploy_mode},#{t.args},#{t.config}," +
            "#{t.email},#{t.send},#{t.restart},#{t.retries})"})
    void save(@Param("t") Job job);

    @Select("select * from job where id = #{id}")
    Job findOne(@Param("id") Integer id);

    @Delete("delete from job where id = #{id}")
    void delete(@Param("id") Integer id);

    @Insert("update job set " +
            "app_id = #{t.app_id}," +
            "state  = #{t.state}," +
            " retries = #{t.retries}" +
            " where id = #{t.id}")
    void update(@Param("t")Job job);

}
