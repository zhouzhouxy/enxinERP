package com.asura.enxin.utils;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/26/026 20:09
 */
@Component("productIdGenerator")
public class IdGenerator {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //产品id
    public String generatorId(){
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        StringBuffer sb=new StringBuffer();
        sb.append("0").append(now.getYear()).append("0");
       // System.out.println(sb.toString());
        //System.out.println(stringRedisTemplate.opsForValue().get("productId"));
        //Long productId = stringRedisTemplate.opsForValue().increment("productId", 1);
        Jedis jedis = new Jedis("192.168.25.135", 6379,10000);
        jedis.auth("123456");
        String productId = jedis.incrBy("productId", 1).toString();
        sb.append(productId);
       // System.out.println(sb.toString());
        //判断如果是新一年的话重新设置key值
        return sb.toString();
    }

    //设计编号
    public String generatorDesignId(){
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        StringBuffer sb=new StringBuffer();
        sb.append("020400").append(now.getYear()).append("0");
//        Jedis jedis = new Jedis("192.168.25.135", 6379,10000);
//        jedis.auth("123456");
        String productId=stringRedisTemplate.opsForValue().increment("designId",1).toString();
//        String productId = jedis.incrBy("designId", 1).toString();
        sb.append(productId);
        // System.out.println(sb.toString());

        return sb.toString();
    }

    //物料编号
    public String generatorMaterialId(){
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        StringBuffer sb=new StringBuffer();
        sb.append("020300").append(now.getYear()).append("0");
        String productId=stringRedisTemplate.opsForValue().increment("materialId",1).toString();
        sb.append(productId);
        return sb.toString();
    }

    //生产工序编号
    public String generatorProcedureId(){
            //03020020200531100001
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        StringBuffer sb=new StringBuffer();
        sb.append("030").append(now.getYear()).append("0");
        String procedureId=stringRedisTemplate.opsForValue().increment("procedureId",1).toString();
        sb.append(procedureId);
        return sb.toString();
    }

    //生产工序细节编号
    public String generatorProcedureDetailsId(){
        String procedureId=stringRedisTemplate.opsForValue().increment("pcId",1).toString();

        return procedureId;
    }

    //产品生产计划编号03040020200603100001
    public String generatorApplyId(){
        //03020020200531100001
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        StringBuffer sb=new StringBuffer();
        sb.append("030400").append(now.getYear()).append("0");
        String procedureId=stringRedisTemplate.opsForValue().increment("applyId",1).toString();
        sb.append(procedureId);
        return sb.toString();
    }
    //产品生产计划编号03040020200603100001
    public String generatorManufactureId(){
        //03050020200604100001
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        StringBuffer sb=new StringBuffer();
        sb.append("030500").append(now.getYear()).append("0");
        String procedureId=stringRedisTemplate.opsForValue().increment("manufactureId",1).toString();
        sb.append(procedureId);
        return sb.toString();
    }

    public String generatorSpayId(){
        StringBuffer sb=new StringBuffer();
        sb.append("040900").append(DateUtil.format(LocalDateTime.now(),"yyyyMMdd"));
        String spayId = stringRedisTemplate.opsForValue().increment("spayId", 1).toString();
        sb.append(spayId);
        return sb.toString();
    }
}
