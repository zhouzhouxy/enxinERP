package com.asura.enxin.service.impl;

import com.asura.enxin.entity.DFile;
import com.asura.enxin.entity.DModule;
import com.asura.enxin.entity.DModuleDetails;
import com.asura.enxin.entity.dto.DesignDto;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.dto.ModuleDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.DModuleDetailsMapper;
import com.asura.enxin.mapper.DModuleMapper;
import com.asura.enxin.service.IDFileService;
import com.asura.enxin.service.IDModuleService;
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
public class DModuleServiceImpl extends ServiceImpl<DModuleMapper, DModule> implements IDModuleService {

    @Autowired
    private DModuleMapper moduleMapper;

    @Autowired
    private DModuleDetailsMapper detailsMapper;

    @Autowired
    private IDFileService fileService;

    @Autowired
    private IdGenerator generator;

    @Override
    public void addMaterialDesign(ModuleDto dto) {

    }

    //添加产品物料
    @Transactional
    @Override
    public Integer insertModule(ModuleDto item, BigDecimal subTotal) {
        //先组装一个DModule实体类
        DModule d = new DModule();
        //设计编号,等下自动生成
        d.setDesignId(generator.generatorDesignId());
        //产品编号
        d.setProductId(item.getProductId());
        //产品名称
        d.setProductName(item.getProductName());
        //产品I级分类编号
        d.setFirstKindId(item.getFirstKindId());
        //产品I级分类名称
        d.setFirstKindName(item.getFirstKindName());
        //产品II级分类编号
        d.setSecondKindId(item.getSecondKindId());
        //产品II级分类名称
        d.setSecondKindName(item.getSecondKindName());
        //产品III级分类编号
        d.setThirdKindId(item.getThirdKindId());
        //产品III级分类名称
        d.setThirdKindName(item.getThirdKindName());
        //设计人
        d.setDesigner(item.getDesigner());
        //设计要求
        d.setModuleDescribe(item.getModuleDescribe());
        //物料总成本
        d.setCostPriceSum(subTotal);
        //登记人
        d.setRegister(item.getRegister());
        //登记时间
        d.setRegisterTime(item.getRegisterTime2());
        //复核人
//        d.setChecker(item.getChecker());
        //复核时间
//        d.setCheckTime(item.getCheckTime());
        //变更人
        d.setChecker(null);
        //变更时间
//        d.setCheckTime(item.getCheckTime());
        //审核标志
        d.setCheckTag("0");
        //变更标志
        d.setChangeTag("0");


        //插入
        moduleMapper.insert(d);
        return d.getId();
    }

    //查询没有审核设计单
    @Override
    public PageResult<DModule> checkModule(Integer pageNum,Integer pageSize) {
        QueryWrapper<DModule> qw = new QueryWrapper<>();
        //条件是审核没有通过
        qw.lambda().eq(DModule::getCheckTag,"0");
        Page<DModule> dModulePage = moduleMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        return new PageResult<DModule>(dModulePage.getTotal(),dModulePage.getRecords());
    }

    //根据id查询设计单
    @Override
    public DesignDto queryDesignById(Integer id) {

        DesignDto dto = new DesignDto();
        //查询产品
        DModule dModule = moduleMapper.selectById(id);
        //查询物料
        QueryWrapper<DModuleDetails> qw = new QueryWrapper<>();
        qw.lambda().eq(DModuleDetails::getParentId,id);
        List<DModuleDetails> dModuleDetails = detailsMapper.selectList(qw);

        dto.setDModule(dModule);
        dto.setDetails(dModuleDetails);

        return dto;
    }

    @Transactional
    @Override
    public void upDM(DModule dModule) {
        //如果是审核未通过 则查询
        if(dModule.getCheckTag().equals("2")){
            //根据产品编号修改设计单状态为为设计
            fileService.updateDesignState(dModule.getProductId());
        }
        moduleMapper.updateById(dModule);
    }

    //根据条件查询设计单
    @Override
    public PageResult<DModule> queryDesignByCondition(MaterialDto dto) {
        QueryWrapper<DModule> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getFirstKindId())){
            qw.lambda().eq(DModule::getFirstKindId,dto.getFirstKindId());
        }
        if(StringUtils.isNotBlank(dto.getSecondKindId())){
            qw.lambda().eq(DModule::getSecondKindId,dto.getSecondKindId());
        }
        if(StringUtils.isNotBlank(dto.getThirdKindId())){
            qw.lambda().eq(DModule::getThirdKindId,dto.getThirdKindId());
        }
        if(dto.getGoodsId()!=null){
            //通过商品id查询产品编号，再做条件查询
            DFile dFile = this.fileService.querySimple(dto.getGoodsId());
            //获取产品编号做条件查询
            qw.lambda().eq(DModule::getProductId,dFile.getProductId());
        }
        //根据设计单状态查询
        if(dto.getState()!=null){
            if(dto.getState()=="0"){
                qw.lambda().in(DModule::getCheckTag,"0","2");
            }else{
                qw.lambda().eq(DModule::getCheckTag,dto.getState());
            }
        }
        //根据date查询
        if(dto.getDate1()!=null){
            qw.lambda().gt(DModule::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(DModule::getRegisterTime,dto.getDate2());
        }


        Page<DModule> dModulePage = moduleMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);

       return new PageResult<>(dModulePage.getTotal(),dModulePage.getRecords());
    }

    @Transactional
    @Override
    public void changeDM(DModule dModule) {
        //修改审核状态为等待审核
        dModule.setCheckTag("0");
        //变更标志为已变更
        dModule.setChangeTag("1");
        //根据Id修改
        moduleMapper.updateById(dModule);
    }

    //通过产品id查询所有的DModule
    @Override
    public List<DModule> queryListByPId(String productId) {
        QueryWrapper<DModule> qw = new QueryWrapper<>();
        qw.lambda().eq(DModule::getProductId,productId);
        return moduleMapper.selectList(qw);
    }

    @Override
    public DModule selectOneByProductId(String designId) {
        QueryWrapper<DModule> qw = new QueryWrapper<>();
        qw.lambda().eq(DModule::getCheckTag,"1")
                .eq(DModule::getProductId,designId);
        return moduleMapper.selectOne(qw);
    }
}
