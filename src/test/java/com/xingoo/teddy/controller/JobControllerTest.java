package com.xingoo.teddy.controller;

import com.xingoo.teddy.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class JobControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void list() throws Exception {
        // 查询内容类型
        mvc.perform(MockMvcRequestBuilders
                .get("/job/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("page","1")
                .param("size","20")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(mvcResult -> {
                    System.out.println(mvcResult.getResponse().getContentAsString());
                });
    }

}
