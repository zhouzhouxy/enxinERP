package com.asura.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/30/030 20:48
 */

public class BigDecimalTest {

    @Test
    public void t1(){
        System.out.println("t1");
        float f1=8455263f;
        for(int i=0;i<10;i++){
            System.out.println(f1);
        }

        float f2= 8455263f;
        for(int i=0;i<10;i++){
            System.out.println(f2);
            f2++;
        }
    }

    @Test
    public void t2(){
        BigDecimal b1=new BigDecimal(2);
        BigDecimal b3=new BigDecimal(5);
        System.out.println(b1.multiply(b3));
    }
}
