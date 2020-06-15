package com.asura.enxin.service.impl;

import com.asura.enxin.entity.*;
import com.asura.enxin.entity.dto.InnerProcedureModuleDto;
import com.asura.enxin.entity.dto.InnerProduction2Dto;
import com.asura.enxin.entity.dto.InnerProductionDto;
import com.asura.enxin.mapper.MProceduringMapper;
import com.asura.enxin.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class MProceduringServiceImpl extends ServiceImpl<MProceduringMapper, MProceduring> implements IMProceduringService {

    @Autowired
    private IMProcedureService imProcedureService;

    @Autowired
    private IMManufactureService imManufactureService;

    @Autowired
    private MProceduringMapper mProceduringMapper;

    @Autowired
    private IMProcedureModulingService imProcedureModulingService;

    @Autowired
    private IMProcedureModuleService imProcedureModuleService;

    @Transactional
    @Override
    public void addProceduringAndModule(InnerProduction2Dto dto) {
        MProcedure mProcedure = dto.getMProcedure();
        //1.修改生产总表
        //1.1因为修改了一部分，所以生产字段标记为未审核
        //通过生产工序表Mprocedure的父Id查询生产总表详情
        MManufacture mManufacture=imManufactureService.queryManufactureById(mProcedure.getParentId());
        //修改字段manufactureProcedureTag字段为未审核=1
        mManufacture.setManufactureProcedureTag("1");

        imManufactureService.updateManufacture(mManufacture);
        //2.修改生产工序表
        //其实就是根据用户自己的修改工序完成标志，因为已经在前端设置了值，
        // 所以只要根据id修改生产工序表，这个修改是为了后面的审核判断使用
        imProcedureService.updateById(dto.getMProcedure());
        //3.插入数据到生产工序过程记录表(M_PROCEDURING)
        MProceduring mProceduring = dto.getMProceduring();

        //它对应的是生产工序表(M_PROCEDURE)
        mProceduring.setParentId(mProcedure.getParentId());
        mProceduring.setDetailsNumber(mProcedure.getDetailsNumber());
        mProceduring.setProcedureId(mProcedure.getProcedureId());
        mProceduring.setProcedureName(mProcedure.getProcedureName());
        mProceduring.setCostPrice(mProcedure.getCostPrice());
        mProceduring.setCheckTag("0");

        mProceduring.setSubtotal(mProceduring.getCostPrice().multiply(mProceduring.getLabourHourAmount()));
//        mProceduring.setProcedureDescribe(mProcedure.getProce)
        mProceduring.setRegCount(BigDecimal.ONE);
        //插入并获取自动生成id
        Integer integer = addProceduring(mProceduring);

        //4.将数据插入生产工序物料表(M_PROCEDURE_MODULING)
        dto.getMProcedureModule().forEach(item->{
            MProcedureModuling mProcedureModuling = new MProcedureModuling();
            mProcedureModuling.setParentId(integer);
            mProcedureModuling.setDetailsNumber(item.getDetailsNumber());
            mProcedureModuling.setProductId(item.getProductId());
            mProcedureModuling.setProductName(item.getProductName());
            mProcedureModuling.setCostPrice(item.getCostPrice());
            mProcedureModuling.setAmount(BigDecimal.valueOf(item.getThisAmount()));
            mProcedureModuling.setSubtotal(mProcedureModuling.getAmount().
                    multiply(mProcedureModuling.getCostPrice()));
            mProcedureModuling.setCheckTag("0");
            //添加
            imProcedureModulingService.addProcedureModuling(mProcedureModuling);
        });

    }

    ////根据mProcedure中的parentId字段和procedureId字段对应查询MProceduring
    @Override
    public MProceduring queryOneByMProcedure(MProcedure mProcedure) {
        QueryWrapper<MProceduring> qw = new QueryWrapper<>();
        qw.lambda().eq(MProceduring::getParentId,mProcedure.getParentId())
                .eq(MProceduring::getProcedureId,mProcedure.getProcedureId())
                    .eq(MProceduring::getCheckTag,"0"); //没有被审核的
        return mProceduringMapper.selectOne(qw);
    }

    //内部生产审核
    @Transactional
    @Override
    public void checkProceduringAndModule(InnerProductionDto dto) {
       //1.修改生产物料过程记录表
        List<InnerProcedureModuleDto> mProcedureModule1 = dto.getMProcedureModule();
        //2.获取生产工序过程记录表M_proceduring
        MProceduring mp = dto.getMProceduring();

        mProcedureModule1.stream().forEach(item->{
            //审核 重新修改数量和成本小计
            MProcedureModule mProcedureModule = item.getMProcedureModule();
            imProcedureModulingService.updateProcedureModuling(mp.getId(),
                    mProcedureModule.getProductId(),
                    mProcedureModule.getRealAmount(),
                    item.getThisAmount());
            //3.修改生产工序物料表
            //3.1修改实际数量和实际物料成本小计
            //如果实际物料大于0则添加 否则重新设置
            if(item.getThisAmount().intValue()>0){
                mProcedureModule.setRealAmount(mProcedureModule.getRealAmount().add(BigDecimal.valueOf(item.getThisAmount())));
            }else{
                mProcedureModule.setRealAmount(BigDecimal.valueOf(item.getThisAmount()));
            }
            //重新设计实际物料成本小计
            mProcedureModule.setRealSubtotal(mProcedureModule.getRealAmount().multiply(mProcedureModule.getCostPrice()));
            imProcedureModuleService.updateMProcedureModule(mProcedureModule);
        });
        //重新修改生产工序过程记录表M_Proceduring
        List<MProcedureModuling> list=imProcedureModulingService.queryProcedureModuling(mp.getId());

        //获取该工序的所有物料实际成本
        BigDecimal s=list.stream().map(MProcedureModuling::getSubtotal).reduce(BigDecimal.ZERO,BigDecimal::add);

        //重新设置MProceduring的实际工时成本
        mp.setSubtotal(mp.getCostPrice().multiply(mp.getLabourHourAmount()));
        //修改生产登记次数
        mp.setRegCount(mp.getRegCount().add(BigDecimal.ONE));
        mp.setCheckTag("1");
        //修改
        upProceduring(mp);
        //4.重新设计生产工序表M_Procedure
        MProcedure mProcedure1 = dto.getMProcedure();

        //根据父Id查询生产总表
        MManufacture mManufacture1 = imManufactureService.queryManufactureById(mProcedure1.getParentId());

        mProcedure1.setRealLabourHourAmount(mProcedure1.getRealLabourHourAmount().add(mp.getLabourHourAmount()));
        mProcedure1.setRealSubtotal(mProcedure1.getRealSubtotal().add(mp.getSubtotal()));
        mProcedure1.setRealModuleSubtotal(mProcedure1.getRealModuleSubtotal().add(s));
        //修改工序完成标志
        if(mProcedure1.getProcedureFinishTag().equals("1")){
            //用户选择的是未完成
            //重新设置工序完成标志为未开始
            mProcedure1.setProcedureFinishTag("0");
        }else{
            mProcedure1.setProcedureFinishTag("3");
            //设置工序交接标志
            //工序完成标志为已审核所以工序交接可以开始
            mProcedure1.setProcedureTransferTag("0");
            //交接时显示投产数量procedureTransferTag=生产总表的投产数量
            mProcedure1.setDemandAmount(mManufacture1.getAmount());
        }
        //修改
        imProcedureService.updateMProcedure(mProcedure1);

        //5.修改生产总表
//        MManufacture mManufacture1 = imManufactureService.queryManufactureById(mProcedure1.getParentId());
        List<MProcedure> mProcedures = imProcedureService.selectListByPId(mManufacture1.getId());
        //修改生产过程标志
        //判断生产工序表下所有工序完成标志是否都是未开始或是已审核
        AtomicReference<Boolean> flag= new AtomicReference<>(true);
        mProcedures.stream().forEach(item->{
            if(item.getProcedureFinishTag().equals("2")||item.getProcedureFinishTag().equals("1")
                                ||item.getProcedureTransferTag().equals("1")){
                flag.set(false);
            }
        });
        if(flag.get()){
            mManufacture1.setManufactureProcedureTag("0");
        }

        //修改实际工时总成本

        //获取物料总成本
        BigDecimal realModuleCostPriceSum=mProcedures.stream().map(MProcedure::getRealModuleSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        //获取工时总成本
        BigDecimal realLabourCostPriceSum =mProcedures.stream().map(MProcedure::getRealSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        //修改
        mManufacture1.setRealModuleCostPriceSum(realModuleCostPriceSum);
        mManufacture1.setRealLabourCostPriceSum(realLabourCostPriceSum);

        imManufactureService.updateManufacture(mManufacture1);
        /*

        //1.修改生产总表M_MANUFACTURE
        //1.1添加实际物料总成本  1.2实际设计工时总成本    1.3 生产过程标志
        MProcedure mProcedure = dto.getMProcedure();
        //根据mProcedure中的parentId查询M_Manufacture生产总表
        MManufacture mManufacture = imManufactureService.queryManufactureById(mProcedure.getParentId());
        //查询实际物料总成本
        List<InnerProcedureModuleDto> mProcedureModule = dto.getMProcedureModule();
        BigDecimal realModuleCostPriceSum=BigDecimal.ZERO;
        */
        /*for (InnerProcedureModuleDto item : mProcedureModule) {
            //3修改生产工序物料表：M_Procedure_Module
            //3.1修改实际数量
            MProcedureModule mp = item.getMProcedureModule();
            mp.setRealAmount(mp.getRealAmount().add(BigDecimal.valueOf(item.getThisAmount())));
            //修改实际物料成本小计
            mp.setRealSubtotal(mp.getRealAmount().multiply(mp.getCostPrice()));
            //根据id修改
            imProcedureModuleService.updateMProcedureModule(mp);
            realModuleCostPriceSum = realModuleCostPriceSum.add(item.getMProcedureModule().getCostPrice().multiply(BigDecimal.valueOf(item.getThisAmount())));
        }*/
        /*

        //设置工时总成本
        MProceduring mProceduring = dto.getMProceduring();


        //查询工序的公时总成本
        BigDecimal realLabourHourAmount = mProceduring.getCostPrice().multiply(mProcedure.getLabourHourAmount());



        //设置生产总表的实际物料总成本和工时总成本
        mManufacture.setRealModuleCostPriceSum(mManufacture.getModuleCostPriceSum().add(realModuleCostPriceSum));
        mManufacture.setRealLabourCostPriceSum(mManufacture.getRealLabourCostPriceSum().add(realLabourHourAmount));
        //修改生产过程标志为待登记 manufacture_procedure_tag=0
        mManufacture.setManufactureProcedureTag("0");

        //根据id修改
        imManufactureService.updateManufacture(mManufacture);
        //2.修改生产工序表
        //2.1实际工时
        mProcedure.setRealLabourHourAmount(mProcedure.getRealLabourHourAmount().add(mProceduring.getLabourHourAmount()));
        //2.2实际工时成本
        mProcedure.setRealSubtotal(mProcedure.getRealLabourHourAmount().multiply(mProcedure.getCostPrice()));
        //2.3实际物料成本
        mProcedure.setRealModuleSubtotal(mProcedure.getRealModuleSubtotal().add(realModuleCostPriceSum));
        //2.4修改工序完成状态 如果用户选择未完成，则工序完成标志为工序为完成=0，
        // 选择完成则工序完成标志为已审核=4
        if(mProcedure.getProcedureFinishTag().equals("1")){
            mProcedure.setProcedureFinishTag("0");
        }else{
            mProcedure.setProcedureFinishTag("4");
        }
        //根据id修改该工序
        imProcedureService.updateMProcedure(mProcedure);
*/

    }

    //根据工序id查询该工序的生产详情
    @Override
    public List<MProceduring> queryProceduring(Integer id) {
        //生产工序和生产工序详情相同之处在于同一个父Id
        MProcedure mProcedure = imProcedureService.queryById(id);
        //根据父id和工序编号查询生产工序过程记录
        QueryWrapper<MProceduring> qw = new QueryWrapper<>();
        qw.lambda().eq(MProceduring::getParentId,mProcedure.getParentId())
            .eq(MProceduring::getProcedureId,mProcedure.getProcedureId());

        return mProceduringMapper.selectList(qw);
    }

    @Transactional
    public Integer addProceduring(MProceduring mProceduring){
        mProceduringMapper.insert(mProceduring);
        return mProceduring.getId();
    }

    @Transactional
    public void upProceduring(MProceduring mProceduring){
        mProceduringMapper.updateById(mProceduring);
    }
}
