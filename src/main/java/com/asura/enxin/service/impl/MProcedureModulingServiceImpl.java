package com.asura.enxin.service.impl;

import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.MProcedureModule;
import com.asura.enxin.entity.MProcedureModuling;
import com.asura.enxin.entity.MProceduring;
import com.asura.enxin.entity.dto.InnerProcedureModuleDto;
import com.asura.enxin.entity.dto.InnerProductionDto;
import com.asura.enxin.entity.dto.ProcedureAndModuleDto;
import com.asura.enxin.mapper.MProcedureModulingMapper;
import com.asura.enxin.service.IMProcedureModuleService;
import com.asura.enxin.service.IMProcedureModulingService;
import com.asura.enxin.service.IMProceduringService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_proceduring`(`ID`) 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Service
public class MProcedureModulingServiceImpl extends ServiceImpl<MProcedureModulingMapper, MProcedureModuling> implements IMProcedureModulingService {

    @Autowired
    private MProcedureModulingMapper mProcedureModulingMapper;

    @Autowired
    private IMProceduringService imProceduringService;


    @Autowired
    private IMProcedureModulingService imProcedureModulingService;

    @Autowired
    private IMProcedureModuleService imProcedureModuleService;

    //添加单个生产工序物料过程记录
    @Transactional
    @Override
    public void addProcedureModuling(MProcedureModuling mProcedureModuling) {
        mProcedureModulingMapper.insert(mProcedureModuling);
    }

    //根据工序查询物料
    @Override
    public InnerProductionDto queryProceduringAndModuling(MProcedure mProcedure) {
        InnerProductionDto dto = new InnerProductionDto();
        //1.先查询生产工序过程记录(M_PROCEDURING)
        //根据mProcedure中的parentId字段和procedureId字段对应查询MProceduring
        MProceduring mProceduring=imProceduringService.queryOneByMProcedure(mProcedure);
        //2.查询该工序下的物料
        ProcedureAndModuleDto procedureAndModuleDto = imProcedureModuleService.queryListByPId(mProcedure.getId());
        List<MProcedureModule> list = procedureAndModuleDto.getList();
        //根据物料查询本次使用量
        List<InnerProcedureModuleDto> collect = list.stream().map(item -> {
            InnerProcedureModuleDto innerProcedureModuleDto = new InnerProcedureModuleDto();
            QueryWrapper<MProcedureModuling> qw = new QueryWrapper<>();
            qw.lambda().eq(MProcedureModuling::getParentId, mProceduring.getId())
                    .eq(MProcedureModuling::getDetailsNumber, item.getDetailsNumber())
                    .eq(MProcedureModuling::getProductId, item.getProductId());
            MProcedureModuling mProcedureModuling = mProcedureModulingMapper.selectOne(qw);
            innerProcedureModuleDto.setThisAmount(mProcedureModuling.getAmount().intValue());
            innerProcedureModuleDto.setMProcedureModule(item);
            return innerProcedureModuleDto;
        }).collect(Collectors.toList());
        dto.setMProceduring(mProceduring);
        dto.setMProcedureModule(collect);
        return dto;
    }

    //根据父id和产品id重新修改
    @Transactional
    @Override
    public void updateProcedureModuling(Integer id, String productId, BigDecimal realAmount, Integer thisAmount) {
        QueryWrapper<MProcedureModuling> qw = new QueryWrapper<>();
        qw.lambda().eq(MProcedureModuling::getProductId,productId)
                .eq(MProcedureModuling::getParentId,id);
        //查询单个
        MProcedureModuling mProcedureModuling = mProcedureModulingMapper.selectOne(qw);
        //判断生产工序物料表中实际使用数量是不是大于0
        //大于0添加
        if(realAmount.intValue()>0){
            mProcedureModuling.setAmount(mProcedureModuling.getAmount().add(BigDecimal.valueOf(thisAmount)));
         }else{
            //等于0为第一次设计
            mProcedureModuling.setAmount(BigDecimal.valueOf(thisAmount));
        }
        //重新设置物料成本
        mProcedureModuling.setSubtotal(mProcedureModuling.getAmount().multiply(mProcedureModuling.getCostPrice()));

        //设置审核状态
        mProcedureModuling.setCheckTag("1");
        //修改
        mProcedureModulingMapper.updateById(mProcedureModuling);
    }

    @Override
    public List<MProcedureModuling> queryProcedureModuling(Integer id) {
        QueryWrapper<MProcedureModuling> qw = new QueryWrapper<>();
        qw.lambda().eq(MProcedureModuling::getParentId,id);
        return mProcedureModulingMapper.selectList(qw);
    }
}
