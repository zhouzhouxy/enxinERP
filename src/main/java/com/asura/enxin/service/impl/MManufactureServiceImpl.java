package com.asura.enxin.service.impl;

import com.asura.enxin.entity.*;
import com.asura.enxin.entity.dto.ApplyConditionDto;
import com.asura.enxin.entity.dto.ManufactureDto;
import com.asura.enxin.entity.dto.ProcedureDesignDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.mapper.MManufactureMapper;
import com.asura.enxin.service.*;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Service
public class MManufactureServiceImpl extends ServiceImpl<MManufactureMapper, MManufacture> implements IMManufactureService {

    @Autowired
    private MManufactureMapper manufactureMapper;

    @Autowired
    private IMApplyService imApplyService;

    @Autowired
    private IMDesignProcedureService procedureService;

    @Autowired
    private IMDesignProcedureDetailsService detailsService;

    @Autowired
    private IMDesignProcedureModuleService procedureModuleService;

    @Autowired
    private IMProcedureModuleService moduleService;

    @Autowired
    private IMProcedureService imProcedureService;

    @Autowired
    private ISPayService isPayService;

    @Autowired
    private ISPayDetailsService isPayDetailsService;

    @Autowired
    private IdGenerator idGenerator;

    @Transactional
    @Override
    public void addManufacture(MManufacture mManufacture) {

        mManufacture.setManufactureId(idGenerator.generatorManufactureId());
        mManufacture.setCheckTag("0");
        mManufacture.setManufactureProcedureTag("0");


        //设计物料总成本
        BigDecimal moduleSum=BigDecimal.ZERO;
        //设计工时总成本
        BigDecimal labourSum=BigDecimal.ZERO;

        //查看工序
        MDesignProcedure designProcedure = procedureService.queryProcedureByProductId(mManufacture.getProductId());

        //设置物料总成本
        mManufacture.setModuleCostPriceSum(designProcedure.getModuleCostPriceSum().multiply(mManufacture.getAmount()));
        mManufacture.setLabourCostPriceSum(designProcedure.getCostPriceSum().multiply(mManufacture.getAmount()));

        //修改mApply中MANUFACTURE_TAG为已派工
        manufactureMapper.insert(mManufacture);
        imApplyService.upManufactureTag(mManufacture.getApplyIdGroup());

        //还需要添加 生产工序表MProcedure和生产工序物料表 MProcedureModule

        //根据工序查询工序细节
        List<MDesignProcedureDetails> details = detailsService.selectListByParentId(designProcedure.getId());

        MProcedure mProcedure = new MProcedure();
        mProcedure.setParentId(mManufacture.getId());


        for (int i = 0; i < details.size(); i++) {
            MDesignProcedureDetails details1 = details.get(i);
            MProcedure mp = new MProcedure();
            mp.setParentId(mManufacture.getId());
            mp.setDetailsNumber(BigDecimal.valueOf(i+1));
            mp.setProcedureId(details1.getProcedureId());
            mp.setProcedureName(details1.getProcedureName());
            mp.setLabourHourAmount(details1.getLabourHourAmount().multiply(mManufacture.getAmount()));
            mp.setRealLabourHourAmount(BigDecimal.valueOf(0));
            mp.setSubtotal(details1.getSubtotal().multiply(mManufacture.getAmount()));
            mp.setRealSubtotal(BigDecimal.valueOf(0));
            mp.setModuleSubtotal(details1.getModuleSubtotal().multiply(mManufacture.getAmount()));
            mp.setRealModuleSubtotal(BigDecimal.valueOf(0));
            mp.setCostPrice(details1.getCostPrice().multiply(mManufacture.getAmount()));
            mp.setDemandAmount(BigDecimal.valueOf(0));
            mp.setRealAmount(BigDecimal.valueOf(0));
            mp.setProcedureFinishTag("0");
            mp.setProcedureTransferTag("0");

            //插入
            imProcedureService.insert(mp);
            //根据工序id查询物料细节
            List<MDesignProcedureModule> list = procedureModuleService.selectByPId(details1.getId());
            for (int i1 = 0; i1 < list.size(); i1++) {
                MDesignProcedureModule module = list.get(i1);
                MProcedureModule mpm = new MProcedureModule();
                mpm.setParentId(mp.getId());
                mpm.setDetailsNumber(BigDecimal.valueOf(i1+1));
                mpm.setProductId(module.getProductId());
                mpm.setProductName(module.getProductName());
                mpm.setCostPrice(module.getCostPrice());
                mpm.setRenewAmount(BigDecimal.valueOf(0));
                mpm.setAmount(module.getAmount().multiply(mManufacture.getAmount()));
                mpm.setSubtotal(module.getSubtotal().multiply(mManufacture.getAmount()));
                mpm.setRealSubtotal(BigDecimal.valueOf(0));
                //插入
                moduleService.insert(mpm);
            }
        }
//
//        mManufacture.setModuleCostPriceSum(moduleSum);
//        mManufacture.setLabourCostPriceSum(labourSum);
//        //修改生产总表设计物料总成本和工时总成本
//        manufactureMapper.updateById(mManufacture);

    }

    @Override
    public PageResult<MManufacture> queryCheck(Integer pageNum, Integer pageSize) {
        QueryWrapper<MManufacture> qw = new QueryWrapper<>();
        //        等待审核
        qw.lambda().eq(MManufacture::getCheckTag,"0");
        Page<MManufacture> page = manufactureMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        return new PageResult<>(page.getTotal(),page.getRecords());
    }

    @Override
    public ManufactureDto queryDetailById(Integer id) {
        MManufacture mManufacture = manufactureMapper.selectById(id);

        //根据产品id查询工序
        //查看工序
        MDesignProcedure designProcedure = procedureService.queryProcedureByProductId(mManufacture.getProductId());
        //根据工序查询工序细节
        List<MDesignProcedureDetails> details = detailsService.selectListByParentId(designProcedure.getId());
        ManufactureDto dto = new ManufactureDto();
        dto.setMManufacture(mManufacture);
        //dto.setList(details);
        //dto.setDesignProcedure(designProcedure);
        return dto;
    }

    @Transactional
    @Override
    public void upManufacture(MManufacture manufacture) {
        //如果审核不通过
        if(manufacture.getCheckTag().equals("2")){
            //修改MAPPly表
            imApplyService.upManufactureTag2(manufacture.getApplyIdGroup());
        }else{
            //如果审核通过
            //通过manufacture的id查询生产工序表
            List<MProcedure> list= imProcedureService.selectListByPId(manufacture.getId());
            list.stream().forEach(item->{
                SPay sPay = new SPay();
                sPay.setPayId(idGenerator.generatorSpayId());
                sPay.setReason("1");
                sPay.setCheckTag("1");

                sPay.setReasonexact("派工单"+item.getParentId()+"-"+item.getProcedureName());
                sPay.setPayTag("1");
                //根据procedureId查询细节
                List<MProcedureModule>  modules =moduleService.selectByPId(item.getId());
//                List<MDesignProcedureModule> modules = procedureModuleService.selectByPId(item.getId());
                //查询每个工序需要多少物料
                BigDecimal s=modules.stream().map(MProcedureModule::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal s2=modules.stream().map(MProcedureModule::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add);

                sPay.setCostPriceSum(s2);
                sPay.setAmountSum(s);
                Integer pId = isPayService.insert(sPay);
                //循环添加出库明细表
                modules.stream().forEach(item2->{
                    SPayDetails payDetails = new SPayDetails();
                    payDetails.setParentId(pId);
                    payDetails.setProductName(item2.getProductName());
                    payDetails.setProductId(item2.getProductId());
                    payDetails.setAmount(item2.getAmount());
                    payDetails.setCostPrice(item2.getCostPrice());
                    payDetails.setSubtotal(item2.getAmount().multiply(item2.getCostPrice()));
                    payDetails.setPayTag("1");
                    isPayDetailsService.insert(payDetails);
                });

            });
        }
        manufactureMapper.updateById(manufacture);
    }



    @Override
    public PageResult<MManufacture> queryByCondtion(ApplyConditionDto dto) {
        QueryWrapper<MManufacture> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getApplyId())){
            qw.lambda().like(MManufacture::getManufactureId,dto.getApplyId());
        }
        if(StringUtils.isNotBlank(dto.getKeyword())){
            qw.lambda().like(MManufacture::getDesigner,dto.getKeyword())
                    .or().like(MManufacture::getProductName,dto.getKeyword())
                    .or().like(MManufacture::getProductDescribe,dto.getKeyword())
                    .or().like(MManufacture::getRemark,dto.getKeyword());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(MManufacture::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().gt(MManufacture::getRegisterTime,dto.getDate1());
        }
        if(StringUtils.isNotBlank(dto.getState())){
            qw.lambda().eq(MManufacture::getCheckTag,dto.getState());
        }
        Page<MManufacture> mApplyPage = manufactureMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);

        return new PageResult<>(mApplyPage.getTotal(),mApplyPage.getRecords());
    }

    @Override
    public MManufacture queryManufactureById(Integer pId) {
        return manufactureMapper.selectById(pId);
    }


}
