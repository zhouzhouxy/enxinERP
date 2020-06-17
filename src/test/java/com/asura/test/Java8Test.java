package com.asura.test;

import com.asura.enxin.service.ISPayDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/9/009 19:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml" )
public class Java8Test {

    @Autowired
    private ISPayDetailsService isPayDetailsService;

    @Test
    public void t1(){
        System.out.println(isPayDetailsService.isAllPayTagPass(19));
    }
}
