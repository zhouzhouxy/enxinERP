package com.asura.enxin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.asura.enxin.entity.*;
import com.asura.enxin.entity.dto.SCellDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.SCellMapper;
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
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class SCellServiceImpl extends ServiceImpl<SCellMapper, SCell> implements ISCellService {

    @Autowired
    private SCellMapper sCellMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private IDFileService fileService;

    @Autowired
    private ISPayService isPayService;

    @Autowired
    private ISPayDetailsService isPayDetailsService;

    @Autowired
    private IMProcedureService imProcedureService;

    @Autowired
    private IMProcedureModuleService imProcedureModuleService;

    @Autowired
    private ISGatherDetailsService isGatherDetailsService;

    //添加
    @Transactional
    @Override
    public void insert(SCell sCell,Integer fileId) {
        DFile dFile = fileService.querySimple(fileId);
        sCell.setProductId(dFile.getProductId());
        sCell.setProductName(dFile.getProductName());
        sCell.setFirstKindId(dFile.getFirstKindId());
        sCell.setFirstKindName(dFile.getFirstKindName());
        sCell.setSecondKindId(dFile.getSecondKindId());
        sCell.setSecondKindName(dFile.getSecondKindName());
        sCell.setThirdKindId(dFile.getThirdKindId());
        sCell.setThirdKindName(dFile.getThirdKindName());
        sCell.setAmount(BigDecimal.valueOf(0));
         sCell.setStoreId(idGenerator.generatorStoreId());
        sCell.setCheckTag("0");
        sCellMapper.insert(sCell);
        //修改DFile表
        fileService.updateStockTag(fileId);
    }

    //根据条件查询
    @Override
    public PageResult<SCell> querySCellByCondition(SCellDto dto) {
        QueryWrapper<SCell> qw=new QueryWrapper();
        if(!StringUtils.isEmpty(dto.getFirstKindId())){
            qw.lambda().eq(SCell::getFirstKindId,dto.getFirstKindId());
        }
        if(!StringUtils.isEmpty(dto.getSecondKindId())){
            qw.lambda().eq(SCell::getSecondKindId,dto.getSecondKindId());
        }
        if(!StringUtils.isEmpty(dto.getThirdKindId())){
            qw.lambda().eq(SCell::getThirdKindId,dto.getThirdKindId());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(SCell::getRegisterTime, DateUtil.format(dto.getDate1(), "yyyy-MM-dd") );
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(SCell::getRegisterTime,DateUtil.format(dto.getDate2(), "yyyy-MM-dd"));
        }
        if(dto.getProductId()!=null){
            qw.lambda().eq(SCell::getProductId,dto.getProductId());
        }
        if(StringUtils.isNotBlank(dto.getCheckTag())){
             qw.lambda().eq(SCell::getCheckTag,dto.getCheckTag());
        }


        Page<SCell> dFilePage = sCellMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);
        return new PageResult<>(dFilePage.getTotal(),dFilePage.getRecords());
    }


    //修改sCell实体类
    @Override
    public void updateSCell(SCell sCell) {
        sCellMapper.updateById(sCell);
    }

    //根据id查询
    @Override
    public SCell querySCellById(Integer id) {
        return sCellMapper.selectById(id);
    }

    @Override
    public SCell queryScellByProductId(String productId) {
        QueryWrapper<SCell> qw = new QueryWrapper<>();
        qw.lambda().eq(SCell::getProductId,productId);
        return sCellMapper.selectOne(qw);
    }

    //入库调度
    @Transactional
    @Override
    public void dispatcher(Integer entryAmount, Integer gdId, Integer scellId) {
        //先查询库存单元表
        SCell sCell = sCellMapper.selectById(scellId);
        sCell.setAmount(sCell.getAmount().add(BigDecimal.valueOf(entryAmount)));
        //修改
        sCellMapper.updateById(sCell);
        //修改入库明细表
        isGatherDetailsService.updateStockTag(gdId);
        //
    }

    //出库调度
    @Transactional
    @Override
    public void outDispatcher(Integer outAmount, Integer sdId, Integer sCellId) {
        SCell sCell = sCellMapper.selectById(sCellId);
        //1修改库存 ,查询当前库存数量减去出库数量
        sCell.setAmount(sCell.getAmount().subtract(BigDecimal.valueOf(outAmount)));
        //2修改出库明细表出库标志和确认出库数
        isPayDetailsService.updatePaidAmountAndPayTag(outAmount,sdId);
        //2.1.先根据出库明细查询出库表中的父Id
        SPayDetails sPayDetails=isPayDetailsService.queryById(sdId);
        //2.2.根据出库明细表中父Id查询出库表中的详细理由
        SPay sPay = isPayService.querySPayById(sPayDetails.getParentId());
        /**
         *修改出库标志和确认出库总件数
         *只有当该出库表下的所有出库明细的出库标志都变成了已调度，
         *才能修改出库表中出库标志为已调度
         */
        Boolean flag=isPayDetailsService.isAllPayTagPass(sPay.getId());
        if(flag){
            sPay.setPayTag("2");
        }
        //如果确认保护库总件数为null则修改为0
        if(sPay.getPaidAmountSum()==null){
            sPay.setPaidAmountSum(BigDecimal.ZERO);
        }
        //重新设置确认出库总件数
        sPay.setPaidAmountSum(sPay.getPaidAmountSum().add(BigDecimal.valueOf(outAmount)));
        //修改
        isPayService.updateSpay(sPay);
        //修改生产工序物料表的补充数量
        /*
         *1.通过S_PAY出库表的详细理由如：派工单1-组装
         *2.通过查询条件到派工单1(parentId) 和 工序是组装在
         * m_procedure生产工序表中查询到id
         * 在通过id和产品编号查询到m_procedure_module中查到具体的某个值
         * 为什么要加产品编号是因为一个id可以对应多个m_procedure_module行
         */
        String reasonexact = sPay.getReasonexact();
        int i = reasonexact.indexOf("-");
        //获取到派工单序号
        String workOrder= reasonexact.substring(3,i);
        //获取工序名称
        String procedureName=reasonexact.substring(i+1,reasonexact.length());
        MProcedure mProcedure=imProcedureService.queryOneByOther(workOrder,procedureName);
        //获取产品编号
        String productId=sCell.getProductId();
        imProcedureModuleService.updateRenewAmount(outAmount,mProcedure.getId(),productId);
    }

    @Override
    public PageResult<SCell> dynamicStockQuery(SCellDto dto) {
        QueryWrapper<SCell> qw=new QueryWrapper();
        if(!StringUtils.isEmpty(dto.getFirstKindId())){
            qw.lambda().eq(SCell::getFirstKindId,dto.getFirstKindId());
        }
        if(!StringUtils.isEmpty(dto.getSecondKindId())){
            qw.lambda().eq(SCell::getSecondKindId,dto.getSecondKindId());
        }
        if(!StringUtils.isEmpty(dto.getThirdKindId())){
            qw.lambda().eq(SCell::getThirdKindId,dto.getThirdKindId());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(SCell::getRegisterTime, DateUtil.format(dto.getDate1(), "yyyy-MM-dd") );
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(SCell::getRegisterTime,DateUtil.format(dto.getDate2(), "yyyy-MM-dd"));
        }
        if(dto.getProductId()!=null){
            qw.lambda().eq(SCell::getProductId,dto.getProductId());
        }
        if(StringUtils.isNotBlank(dto.getCheckTag())){
            qw.lambda().eq(SCell::getCheckTag,dto.getCheckTag());
        }

        List<String> strings = isPayDetailsService.selectProductId();

        qw.lambda().in(SCell::getProductId,strings);

        Page<SCell> dFilePage = sCellMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);
        return new PageResult<>(dFilePage.getTotal(),dFilePage.getRecords());
    }

}
