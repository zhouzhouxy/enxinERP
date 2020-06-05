package com.asura.enxin.service.impl;

import com.asura.enxin.entity.DConfigPublicChar;
import com.asura.enxin.mapper.DConfigPublicCharMapper;
import com.asura.enxin.service.IDConfigPublicCharService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/26/026 16:40
 */
@Service
public class DConfigPublicCharServiceImpl implements IDConfigPublicCharService {

    @Autowired
    private DConfigPublicCharMapper charMapper;

    //查询用途
    @Override
    public List<String> queryUseType() {
        QueryWrapper<DConfigPublicChar> qw=new QueryWrapper<>();
        qw.lambda().eq(DConfigPublicChar::getKind,"产品用途");
        List<DConfigPublicChar> pc = charMapper.selectList(qw);
        List<String> useType=
                pc.stream().map(configs->configs.getTypeName()).collect(Collectors.toList());

        return useType;
    }

    //查询档次
    @Override
    public List<String> queryGrade() {
        QueryWrapper<DConfigPublicChar> qw=new QueryWrapper<>();
        qw.lambda().eq(DConfigPublicChar::getKind,"档次级别");
        List<DConfigPublicChar> pc = charMapper.selectList(qw);
        List<String> level=
                pc.stream().map(configs->configs.getTypeName()).collect(Collectors.toList());
        return level;
    }
}
