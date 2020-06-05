package com.asura.enxin.service.impl;

import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.MDesignProcedureDetails;
import com.asura.enxin.entity.MDesignProcedureModule;
import com.asura.enxin.mapper.MDesignProcedureDetailsMapper;
import com.asura.enxin.service.IDModuleDetailsService;
import com.asura.enxin.service.IMDesignProcedureDetailsService;
import com.asura.enxin.service.IMDesignProcedureModuleService;
import com.asura.enxin.service.IMDesignProcedureService;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_design_procedure`(`ID`) 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-01
 */
@Service
public class MDesignProcedureDetailsServiceImpl implements IMDesignProcedureDetailsService{

    @Autowired
    private MDesignProcedureDetailsMapper detailsMapper;

    @Autowired
    private IMDesignProcedureModuleService moduleService;

    @Autowired
    private IDModuleDetailsService detailsService;


    @Autowired
    private IMDesignProcedureService procedureService;


    @Autowired
    private IdGenerator idGenerator;

    //添加产品工序详细
    @Transactional
    @Override
    public void addProcedureDetails(List<MDesignProcedureDetails> list, Integer id) {

        for (int i = 0; i < list.size(); i++) {
            MDesignProcedureDetails dpd = list.get(i);
            dpd.setDetailsNumber(BigDecimal.valueOf(i+1));
            dpd.setParentId(id);
            //当前工序物料设计标志
            dpd.setDesignModuleTag("0");
            //当前工序物料变更标志
            dpd.setDesignModuleChangeTag("0");
            detailsMapper.insert(dpd);
        }
    }

    @Override
    public List<MDesignProcedureDetails> selectListByParentId(Integer id) {
        QueryWrapper<MDesignProcedureDetails> details = new QueryWrapper<>();
        details.lambda().eq(MDesignProcedureDetails::getParentId,id);

        return detailsMapper.selectList(details);
    }

    @Transactional
    @Override
    public void deleteByParentId(Integer id) {
        UpdateWrapper<MDesignProcedureDetails> uw = new UpdateWrapper<>();
        uw.lambda().eq(MDesignProcedureDetails::getParentId,id);
        detailsMapper.delete(uw);
    }

    @Override
    public MDesignProcedureDetails selectById(Integer id) {
        return detailsMapper.selectById(id);
    }

    //修改当前工序的物料标志 和物料成本
    @Transactional
    @Override
    public void update(BigDecimal subtotal,Integer id) {
        MDesignProcedureDetails dpd = new MDesignProcedureDetails();
        UpdateWrapper<MDesignProcedureDetails> uw = new UpdateWrapper<>();
        uw.lambda().set(MDesignProcedureDetails::getModuleSubtotal,subtotal)
                .set(MDesignProcedureDetails::getDesignModuleTag,"1")
                .eq(MDesignProcedureDetails::getId,id);
        detailsMapper.update(dpd,uw);
    }

    @Transactional
    @Override
    public void upById(Integer id) {
        MDesignProcedureDetails dpd = new MDesignProcedureDetails();
        UpdateWrapper<MDesignProcedureDetails> uw = new UpdateWrapper<>();
        uw.lambda().set(MDesignProcedureDetails::getModuleSubtotal,0)
                .set(MDesignProcedureDetails::getDesignModuleTag,"0")
                .eq(MDesignProcedureDetails::getId,id);
        detailsMapper.update(dpd,uw);
    }

    @Transactional
    @Override
    public void confirmDel(Integer id) {

        //删除之前重新设置物料数量
        List<MDesignProcedureModule> list=moduleService.selectByPId(id);
        //查询该工序详情
        MDesignProcedureDetails dpd = selectById(id);
        //根据工序的父Id查询产品Id
        detailsService.updateResidualAmount2(list,dpd.getParentId());
        moduleService.delByPId(id);
        upById(id);
    }
    @Transactional
    @Override
    public void confirmUp(Integer id) {

        //删除之前重新设置物料数量
        List<MDesignProcedureModule> list=moduleService.selectByPId(id);
        //查询该工序详情
        MDesignProcedureDetails dpd = selectById(id);
        //修改m_desgin_module 中的designModuleTag
        //MDesignProcedure designProcedure = procedureService.selectById(dpd.getParentId());
        //designProcedure.setDesignModuleTag("3");
        procedureService.updateProcedureMTeById(dpd.getParentId(),"3");
        //修改该工序
        dpd.setDesignModuleChangeTag("1");
        detailsMapper.updateById(dpd);
        //根据工序的父Id查询产品Id
        detailsService.updateResidualAmount2(list,dpd.getParentId());
        moduleService.delByPId(id);
        upById(id);
    }
}
