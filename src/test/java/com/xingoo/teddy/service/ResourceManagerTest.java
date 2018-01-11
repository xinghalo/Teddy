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
public class ResourceManagerTest {

    @Test
    public void test1(){
        //Collection<File> files = Resources.listJars();
        //Assert.assertEquals(files.size(),2);
    }

    @Test
    public void test3(){
//        new Thread(()->{
//            while(true){
//                System.out.println(LocalDateTime.now());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void test4(){
//         Thread th = new Thread(()->{
//            try {
//                Process process = Runtime.getRuntime().exec("ping www.baidu.com");
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        });
//        th.setDaemon(false);
//        th.start();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
