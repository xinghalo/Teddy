package com.xingoo.streaming.monitor.service;

import com.xingoo.streaming.monitor.Application;
import com.xingoo.streaming.monitor.manager.EmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class EmailTest {

    @Autowired
    private EmailSender emailSender;

    @Test
    public void send(){
//        emailSender.send("xinghailong@51tiangou.com","自定义主题1","自定义内容");
//        emailSender.send("xinghailong@51tiangou.com","自定义主题2","自定义内容");
//        emailSender.send("xinghailong@51tiangou.com","自定义主题3","自定义内容");
    }
}
