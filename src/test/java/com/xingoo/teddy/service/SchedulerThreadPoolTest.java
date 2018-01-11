package com.xingoo.teddy.service;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerThreadPoolTest {
    @Test
    public void test() {
        System.out.println("start");
        ScheduledExecutorService scheduledThreadPool2 = Executors.newScheduledThreadPool(1);
        scheduledThreadPool2.scheduleAtFixedRate(()->{
            System.out.println(LocalDateTime.now());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },0,5, TimeUnit.SECONDS);
        System.out.println("end");

//        try {
//            Thread.sleep(10000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void test1(){
        Integer a = 0;
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.scheduleWithFixedDelay(()->{
            System.out.println(LocalDateTime.now());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },0,5, TimeUnit.SECONDS);

//        synchronized (this){
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

    @Test
    public void test3(){
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

        for(int i=0; i<3;i++){
            scheduledThreadPool.schedule(()->{
                System.out.println(LocalDateTime.now());
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            },5, TimeUnit.SECONDS);
        }

//        try {
//            this.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
