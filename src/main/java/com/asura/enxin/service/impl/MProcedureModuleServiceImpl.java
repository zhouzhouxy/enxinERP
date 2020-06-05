package com.asura.enxin.service.impl;

import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.MProcedureModule;
import com.asura.enxin.entity.dto.ProcedureAndModuleDto;
import com.asura.enxin.mapper.MProcedureModuleMapper;
import com.asura.enxin.service.IMProcedureModuleService;
import com.asura.enxin.service.IMProcedureService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_procedure`(`ID`) 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Service
public class MProcedureModuleServiceImpl extends ServiceImpl<MProcedureModuleMapper, MProcedureModule> implements IMProcedureModuleService {

    @Autowired
    private MProcedureModuleMapper procedureModuleMapper;

    @Autowired
    private IMProcedureService procedureService;


    @Transactional
    @Override
    public void insert(MProcedureModule mpm) {
        procedureModuleMapper.insert(mpm);
    }

    @Override
    public ProcedureAndModuleDto queryListByPId(Integer pId) {
        QueryWrapper<MProcedureModule> qw = new QueryWrapper<>();
        qw.lambda().eq(MProcedureModule::getParentId,pId);
        List<MProcedureModule> list = procedureModuleMapper.selectList(qw);

        //查询Mprocedure
       MProcedure procedure= procedureService.queryById(pId);
        ProcedureAndModuleDto dto = new ProcedureAndModuleDto();
        dto.setList(list);
        dto.setMProcedure(procedure);
        return dto;
    }

    @Override
    public List<MProcedureModule> selectByPId(Integer id) {
        QueryWrapper<MProcedureModule> qw = new QueryWrapper<>();
        qw.lambda().eq(MProcedureModule::getParentId,id);
        return procedureModuleMapper.selectList(qw);

    }
}
