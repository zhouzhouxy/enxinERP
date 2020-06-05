package com.asura.test;

import cn.hutool.core.date.DateUtil;
import com.asura.enxin.service.IDFileService;
import com.asura.enxin.utils.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/26/026 22:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml" )
public class GeneratorId {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Autowired
    private IdGenerator idGenerator;

    @Test
    public void t1(){
//        String in = stringRedisTemplate.opsForValue().get("in");
//        System.out.println(stringRedisTemplate.opsForValue().increment("in", 2));

        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        StringBuffer sb=new StringBuffer();
        sb.append("0").append(now.getYear()).append("0");
        System.out.println(sb.toString());
        System.out.println(stringRedisTemplate.opsForValue().get("productId"));
        Long productId = stringRedisTemplate.opsForValue().increment("productId", 1);
        sb.append(productId);
        System.out.println(sb.toString());
     }

     @Autowired
    private IDFileService fileService;

    @Test
    public void t2(){
        fileService.delFile(8);
    }

    @Test
    public void t3(){
        System.out.println(idGenerator.generatorDesignId());
    }


    @Test
    public void t4(){
        System.out.println(idGenerator.generatorMaterialId());
    }


    @Test
    public  void t5(){
        stringRedisTemplate.opsForValue().set("applyId","531100000");
        System.out.println(stringRedisTemplate.opsForValue().get("applyId"));
    }

    @Test
    public void t6(){
        stringRedisTemplate.opsForValue().set("manufactureId","604100000");
        System.out.println(stringRedisTemplate.opsForValue().get("manufactureId"));
    }

    @Test
    public void t7(){
        System.out.println();
        StringBuffer sb=new StringBuffer();
        sb.append("040900").append(DateUtil.format(LocalDateTime.now(),"yyyyMMdd"));
        stringRedisTemplate.opsForValue().set("spayId","100000");
        System.out.println(sb.append(stringRedisTemplate.opsForValue().get("spayId")));
    }


}


