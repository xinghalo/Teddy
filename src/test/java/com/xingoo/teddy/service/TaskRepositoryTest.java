package com.xingoo.teddy.service;

import com.xingoo.teddy.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TaskRepositoryTest {

//    @Autowired
//    private TaskJPARepository taskRepository;

    @Test
    public void test(){
//        Task task = taskRepository.findOne("1");
//        System.out.println(task);
    }
}
