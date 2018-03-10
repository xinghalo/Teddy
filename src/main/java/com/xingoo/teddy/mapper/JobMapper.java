package com.xingoo.teddy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface JobMapper {

    @Select("select count(1) from job")
    Integer count() throws Exception;

    /**
     * ALWAYS 不允许手动指定 ID
     * DEFAULT 也可以手动指定ID
     */
    @Update({"create table job(" +
            "id                 INT NOT NULL GENERATED ALWAYS AS IDENTITY," +
            "name               VARCHAR(200)," +
            "applicationId      VARCHAR(200)," +
            "state              VARCHAR(20)," +
            "appResource        VARCHAR(200)," +
            "mainClass          VARCHAR(200)," +
            "master             VARCHAR(20)," +
            "deployMode         VARCHAR(20)," +
            "appArgs            VARCHAR(3000)," +
            "email              VARCHAR(200)," +
            "send               INT," +
            "restart            INT," +
            "retries            INT," +
            "modify_time        DATE"})
    void create();

}
