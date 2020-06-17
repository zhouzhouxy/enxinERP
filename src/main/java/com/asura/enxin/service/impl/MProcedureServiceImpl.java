package com.asura.enxin.service.impl;

import com.asura.enxin.entity.MManufacture;
import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.SGather;
import com.asura.enxin.entity.SGatherDetails;
import com.asura.enxin.entity.dto.ManufactureDto;
import com.asura.enxin.mapper.MProcedureMapper;
import com.asura.enxin.service.*;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.asura.enxin.entity.DFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ISGatherDetailsService isGatherDetailsService;

    @Autowired
    private ISGatherService isGatherService;

    @Override
    public void insert(MProcedure mp) {
        mapper.insert(mp);
    }

    @Autowired
    private IDFileService idFileService;


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

    //工序交接
    @Override
    public void handWork(Integer mProcedureId, Integer quliafyAmount) {
        MProcedure mProcedure = mapper.selectById(mProcedureId);
        //修改生产总表MANUFACTURE_PROCEDURE_TAG生产过程标志为未审核
        MManufacture mManufacture = manufactureService.queryManufactureById(mProcedure.getParentId());
        mManufacture.setManufactureProcedureTag("1");
        //修改
        manufactureService.updateManufacture(mManufacture);


        mProcedure.setRealAmount(BigDecimal.valueOf(quliafyAmount));   //设置工序合格数量
        mProcedure.setProcedureTransferTag("1");    //已交接待审核
        mapper.updateById(mProcedure);
    }

    @Transactional
    @Override
    public void handOverCheck(MProcedure mProcedure, Integer nextId) {
        //修改PROCEDURE_TRANSFER_TAG工序交接标志为已审核
        mProcedure.setProcedureTransferTag("2");
        //根据Id修改
        mapper.updateById(mProcedure);
        //修改后在查询
        //MProcedure mProcedure2 = mapper.selectById(mProcedure.getId());
        //根据nextId查询是否有下一个工序
        MProcedure mProcedure1 = mapper.selectById(nextId);

        MManufacture mManufacture = manufactureService.queryManufactureById(mProcedure.getParentId());
        //将REAL_AMOUNT合格数量交个下个工序
        if(mProcedure1!=null){
            //如果不等于null，表示还有下一个
           mProcedure1.setDemandAmount(mProcedure.getRealAmount());

            List<MProcedure> mProcedures = selectListByPId(mProcedure1.getParentId());
            //修改生产过程标志
            //判断生产工序表下所有工序完成标志是否都是未开始或是已审核
            AtomicReference<Boolean> flag= new AtomicReference<>(true);
            mProcedures.stream().forEach(item->{
                if(item.getProcedureFinishTag().equals("2")
                        ||item.getProcedureFinishTag().equals("1")
                        ||item.getProcedureTransferTag().equals("1")){
                    flag.set(false);
                }
            });
            if(flag.get()){
                mManufacture.setManufactureProcedureTag("0");
            }
            //修改mProcedure1
            mapper.updateById(mProcedure1);
        }else{
            //否则表示所有工序都已经完成交接，修改生产总表MANUFACTURE_PROCEDURE_TAG生产过程已经完工
            mManufacture.setManufactureProcedureTag("2");
            //设置TESTED_AMOUNT合格数量为最后一道工序的合格数量
            mManufacture.setTestedAmount(mProcedure.getRealAmount());
            //插入入库
            SGather sGather = new SGather();
            //设置入库单
            sGather.setGatherId(idGenerator.generatorGatherId());
            //出库详细理由
            sGather.setReason("1"); //生产入库
            sGather.setReasonexact(mManufacture.getManufactureId());//入库详细理由
            sGather.setCheckTag("1");
            sGather.setRegister(mManufacture.getRegister());
            sGather.setRegisterTime(LocalDateTime.now());
            sGather.setCostPriceSum(mManufacture.getRealLabourCostPriceSum().add(mManufacture.getRealModuleCostPriceSum()));
            sGather.setAmountSum(mManufacture.getTestedAmount());
            sGather.setGatherTag("1");
            //插入
            Integer sId=isGatherService.insertSGather(sGather);
            //根据productId查询产品
            DFile dFile=idFileService.queryDFileByProductId(mManufacture.getProductId());

            //插入入库明细表
            SGatherDetails sGatherDetails = new SGatherDetails();
            sGatherDetails.setParentId(sId);
            sGatherDetails.setProductId(mManufacture.getProductId());
            sGatherDetails.setProductName(mManufacture.getProductName());
            sGatherDetails.setProductDescribe(mManufacture.getProductDescribe());
            sGatherDetails.setAmount(mManufacture.getAmount());

            sGatherDetails.setCostPrice(dFile.getRealCostPrice());
            sGatherDetails.setAmountUnit(dFile.getAmountUnit());
            sGatherDetails.setSubtotal(sGatherDetails.getAmount().multiply(sGatherDetails.getCostPrice()));
            sGatherDetails.setGatherTag("1");
            //添加
            isGatherDetailsService.addSimpleSGatherDetails(sGatherDetails);
        }
        manufactureService.updateManufacture(mManufacture);
    }


}