package com.asura.enxin.service.impl;

import com.asura.enxin.entity.MDesignProcedureDetails;
import com.asura.enxin.entity.MDesignProcedureModule;
import com.asura.enxin.entity.MProcedureModule;
import com.asura.enxin.entity.dto.ProcedureDetailDto;
import com.asura.enxin.mapper.MDesignProcedureDetailsMapper;
import com.asura.enxin.mapper.MDesignProcedureModuleMapper;
import com.asura.enxin.service.IDModuleDetailsService;
import com.asura.enxin.service.IMDesignProcedureDetailsService;
import com.asura.enxin.service.IMDesignProcedureModuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_design_procedure_details`(`ID 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Service
public class MDesignProcedureModuleServiceImpl extends ServiceImpl<MDesignProcedureModuleMapper, MDesignProcedureModule> implements IMDesignProcedureModuleService {


    @Autowired
    private MDesignProcedureModuleMapper moduleMapper;

    @Autowired
    private IDModuleDetailsService moduleDetailsService;

    @Autowired
    private IMDesignProcedureDetailsService detailsService;

    @Transactional
    @Override
    public void upProcedure(ProcedureDetailDto dto) {
        //现根据id查询再修改
        //MDesignProcedureDetails details = detailsService.selectById(dto.getId());

        BigDecimal subtotal=BigDecimal.ZERO;
        //循环插入
        for (int i = 0; i < dto.getList().size(); i++) {
            MDesignProcedureModule module = dto.getList().get(i);
            subtotal=subtotal.add(module.getSubtotal());
            //修改物料数量
            moduleDetailsService.updateResidualAmount(module.getAmount(),module.getProductId(),dto.getProductId());
            //插入数据
            module.setDetailsNumber(BigDecimal.valueOf(i+1));
            moduleMapper.insert(module);
        }
        //修改
        detailsService.update(subtotal,dto.getId());

    }

    @Override
    public List<MDesignProcedureModule> selectByPId(Integer pId) {
        QueryWrapper<MDesignProcedureModule> qw = new QueryWrapper<>();
        qw.lambda().eq(MDesignProcedureModule::getParentId,pId);
        return moduleMapper.selectList(qw);
    }

    @Transactional
    @Override
    public void delByPId(Integer id) {
        UpdateWrapper<MDesignProcedureModule> uw = new UpdateWrapper<>();
        uw.lambda().eq(MDesignProcedureModule::getParentId,id);
         moduleMapper.delete(uw);
    }

    @Transactional
    @Override
    public void insert(MProcedureModule mpm) {
//        moduleMapper.insert(mpm);
    }
}
