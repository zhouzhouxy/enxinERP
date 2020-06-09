package com.asura.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/4/004 15:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml" )
public class StringTest {

    @Test
    public void t1(){
        //定义一个字符串
        String str="9";
        String[] split = str.split(",");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }

    @Test
    public void t2(){
        String str="派工单14sdsds4-组装34";
        int i = str.indexOf("-");
        System.out.println(i);
        System.out.println(str.substring(3, i));
        System.out.println(str.substring(i+1, str.length()));
    }
}
