package com.asura.enxin.service;

import com.asura.enxin.entity.DConfigFileKind;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/26/026 16:40
 */
public interface IDConfigPublicCharService  {

    //查询用途类型
    List<String> queryUseType();

    //和档次级别
    List<String> queryGrade();
}
