package com.asura.enxin.service.impl;

import com.asura.enxin.entity.MManufacture;
import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.dto.ManufactureDto;
import com.asura.enxin.mapper.MProcedureMapper;
import com.asura.enxin.service.IMManufactureService;
import com.asura.enxin.service.IMProcedureService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_manufacture`(`ID`) 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Service
public class MProcedureServiceImpl extends ServiceImpl<MProcedureMapper, MProcedure> implements IMProcedureService {


    @Autowired
    private MProcedureMapper mapper;

    @Autowired
    private IMManufactureService manufactureService;
    @Override
    public void insert(MProcedure mp) {
        mapper.insert(mp);
    }

    @Override
    public ManufactureDto queryProcedureByPId(Integer pId) {
        QueryWrapper<MProcedure> qw = new QueryWrapper<>();
        qw.lambda().eq(MProcedure::getParentId,pId);
        List<MProcedure> list = mapper.selectList(qw);
        //查询详情
        MManufacture mManufacture = manufactureService.queryManufactureById(pId);

        ManufactureDto dto = new ManufactureDto();
        dto.setList(list);
        dto.setMManufacture(mManufacture);
        return dto;
    }

    @Override
    public MProcedure queryById(Integer pId) {
        return mapper.selectById(pId);
    }

    @Override
    public List<MProcedure> selectListByPId(Integer id) {
        QueryWrapper<MProcedure> qw = new QueryWrapper<>();
        qw.lambda().eq(MProcedure::getParentId,id);
        return  mapper.selectList(qw);
    }

    //根据父Id和工序名称查询单行数据
    @Override
    public MProcedure queryOneByOther(String workOrder, String procedureName) {
        Integer pId= Integer.valueOf(workOrder);
        QueryWrapper<MProcedure> qw = new QueryWrapper<>();
       qw.lambda().eq(MProcedure::getParentId,pId)
               .eq(MProcedure::getProcedureName,procedureName);
        return mapper.selectOne(qw);
    }

    @Transactional
    @Override
    public void updateMProcedure(MProcedure mProcedure) {
        mapper.updateById(mProcedure);
    }
}
