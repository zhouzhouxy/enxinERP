package com.asura.enxin.service.impl;

import com.asura.enxin.entity.*;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.dto.ProcedureDesignDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.mapper.MDesignProcedureMapper;
import com.asura.enxin.service.*;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
public class MDesignProcedureServiceImpl extends ServiceImpl<MDesignProcedureMapper, MDesignProcedure> implements IMDesignProcedureService {

    @Autowired
    private MDesignProcedureMapper procedureMapper;

    @Autowired
    private IMDesignProcedureDetailsService detailsService;

    @Autowired
    private IMDesignProcedureModuleService moduleService;

    @Autowired
    private IDModuleDetailsService moduleDetailsService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private IDFileService idFileService;
    //添加生产工序设计单
    @Transactional
    @Override
    public void addProcedure(ProcedureDesignDto dto) {
        //先添加产品生产工序
        MDesignProcedure dp = dto.getDesignProcedure();
        dp.setDesignId(idGenerator.generatorProcedureId());
        procedureMapper.insert(dp);
        //循环插入数据到产品生产工序明细表
        detailsService.addProcedureDetails(dto.getList(),dp.getId());
        //修改DFile中产品工序设计为1
        idFileService.upProcedure(dp.getProductId());
    }

    //查询未审核的设计单
    @Override
    public PageResult<MDesignProcedure> checkProcedure(Integer pageNum, Integer pageSize) {

        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();

        qw.lambda().eq(MDesignProcedure::getCheckTag,"0");

        Page<MDesignProcedure> page = procedureMapper.selectPage(new Page<>(pageNum, pageSize), qw);

        return new PageResult<>(page.getTotal(),page.getRecords());
    }

    @Override
    public ProcedureDesignDto queryProcedureById(Integer id) {
        //查询产品生产工序
        MDesignProcedure procedure = procedureMapper.selectById(id);
        //查询产品生产工序明细
        List<MDesignProcedureDetails> details= detailsService.selectListByParentId(id);
        ProcedureDesignDto dto = new ProcedureDesignDto();
        dto.setList(details);
        dto.setDesignProcedure(procedure);
        return dto;
    }

    //审核
    @Transactional
    @Override
    public void passCheck(ProcedureDesignDto dto) {
        //先判断是审核通过还是未通过
        if(dto.getDesignProcedure().getCheckTag().equals("2")){
            //如果未通过修改DFile表工序组成标志为为设计
            idFileService.upProcedureState(dto.getDesignProcedure().getProductId());
            procedureMapper.updateById(dto.getDesignProcedure());
        }else{
            //审核通过
            //1.先修改M_DESIGN_PROCEDURE表字段CHECK_TAG为审核通过
            procedureMapper.updateById(dto.getDesignProcedure());
            //2.先根据parentId删除在重新插入
            detailsService.deleteByParentId(dto.getDesignProcedure().getId());
            //3.重新插入
            detailsService.addProcedureDetails(dto.getList(),dto.getDesignProcedure().getId());
        }
    }

    @Override
    public PageResult<MDesignProcedure> queryPdByCondition(MaterialDto dto) {
        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getFirstKindId())){
            qw.lambda().eq(MDesignProcedure::getFirstKindId,dto.getFirstKindId());
        }
        if(StringUtils.isNotBlank(dto.getSecondKindId())){
            qw.lambda().eq(MDesignProcedure::getSecondKindId,dto.getSecondKindId());
        }
        if(StringUtils.isNotBlank(dto.getThirdKindId())){
            qw.lambda().eq(MDesignProcedure::getThirdKindId,dto.getThirdKindId());
        }
        if(dto.getGoodsId()!=null){
            //通过商品id查询产品编号，再做条件查询
            DFile dFile = this.idFileService.querySimple(dto.getGoodsId());
            //获取产品编号做条件查询
            qw.lambda().eq(MDesignProcedure::getProductId,dFile.getProductId());
        }
        //根据设计单状态查询
        if(dto.getState()!=null){
            if(dto.getState()=="0"){
                qw.lambda().in(MDesignProcedure::getCheckTag,"0","2");
            }else{
                qw.lambda().eq(MDesignProcedure::getCheckTag,dto.getState());
            }
        }
        //根据date查询
        if(dto.getDate1()!=null){
            qw.lambda().gt(MDesignProcedure::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(MDesignProcedure::getRegisterTime,dto.getDate2());
        }
        Page<MDesignProcedure> dModulePage = procedureMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);

        return new PageResult<>(dModulePage.getTotal(),dModulePage.getRecords());
    }

    //分页查询审核通过的工序和物料组成设计为未设计
    @Override
    public PageResult<MDesignProcedure> queryDP(Integer pageNum, Integer pageSize) {
        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();
        qw.lambda().eq(MDesignProcedure::getCheckTag,"1").in(MDesignProcedure::getDesignModuleTag,"0","9");
        Page<MDesignProcedure> page = procedureMapper.selectPage(new Page<>(pageNum, pageSize), qw);

        return new PageResult<>(page.getTotal(),page.getRecords());
    }



    @Override
    public MDesignProcedure selectById(Integer parentId) {
        return procedureMapper.selectById(parentId);
    }

    @Transactional
    @Override
    public void updateProcedure(MDesignProcedure procedure) {
        procedure.setDesignModuleTag("1");
        procedureMapper.updateById(procedure);
    }

    //分页查询需要审核的工序
    @Override
    public PageResult<MDesignProcedure> queryDPCheck(Integer pageNum, Integer pageSize) {
        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();
        qw.lambda().eq(MDesignProcedure::getCheckTag,"1").eq(MDesignProcedure::getDesignModuleTag,"1");
        Page<MDesignProcedure> page = procedureMapper.selectPage(new Page<>(pageNum, pageSize), qw);

        return new PageResult<>(page.getTotal(),page.getRecords());
    }

    @Transactional
    @Override
    public void updateProcedure2(MDesignProcedure procedure) {
         if(procedure.getDesignModuleTag().equals("9")){
            //表示审核未通过
            //修改工序物料为未设计
            List<MDesignProcedureDetails> details = detailsService.selectListByParentId(procedure.getId());

            details.stream().forEach(item->{
                List<MDesignProcedureModule> list = moduleService.selectByPId(item.getId());
                //修改物料可用数量
                moduleDetailsService.updateResidualAmount2(list,procedure.getId());
                //修改工序物料为未设计
                detailsService.upById(item.getId());
                //
                moduleService.delByPId(item.getId());
            });
        }
        procedureMapper.updateById(procedure);
    }

    //查询所有通过审核的产品
    @Override
    public List<MDesignProcedure> selectAllGoods() {
        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();

        qw.lambda().eq(MDesignProcedure::getCheckTag,"1");
        return procedureMapper.selectList(qw);
    }

    @Override
    public PageResult<MDesignProcedure> queryPdByCondition2(MaterialDto dto) {
        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getFirstKindId())){
            qw.lambda().eq(MDesignProcedure::getFirstKindId,dto.getFirstKindId());
        }
        if(StringUtils.isNotBlank(dto.getSecondKindId())){
            qw.lambda().eq(MDesignProcedure::getSecondKindId,dto.getSecondKindId());
        }
        if(StringUtils.isNotBlank(dto.getThirdKindId())){
            qw.lambda().eq(MDesignProcedure::getThirdKindId,dto.getThirdKindId());
        }
        if(dto.getGoodsId()!=null){
            qw.lambda().eq(MDesignProcedure::getId,dto.getGoodsId());
        }
        //根据设计单状态查询
        if(dto.getState()!=null){
            if(dto.getState().equals("0")){
                qw.lambda().in(MDesignProcedure::getDesignModuleTag,"0","1");
            }else if(dto.getState().equals("4")){
                qw.lambda().in(MDesignProcedure::getDesignModuleTag,"2","3");
            }
            else{
                qw.lambda().eq(MDesignProcedure::getDesignModuleTag,dto.getState());
            }
        }
        //根据date查询
        if(dto.getDate1()!=null){
            qw.lambda().gt(MDesignProcedure::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(MDesignProcedure::getRegisterTime,dto.getDate2());
        }
        //只能查询审核通过的
        qw.lambda().eq(MDesignProcedure::getCheckTag,"1");
        Page<MDesignProcedure> dModulePage = procedureMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);

        return new PageResult<>(dModulePage.getTotal(),dModulePage.getRecords());
    }

    @Override
    public void updateProcedureMTeById(Integer parentId, String s) {
        MDesignProcedure designProcedure = new MDesignProcedure();

        UpdateWrapper<MDesignProcedure> uw = new UpdateWrapper<>();
        uw.lambda().set(MDesignProcedure::getDesignModuleTag,s).eq(MDesignProcedure::getId,parentId);
        procedureMapper.update(designProcedure,uw);
    }

    //根据产品编号查询该产品的工序设计或工序物料设计是否完成
    @Override
    public Result queryStateByPId(String pId) {
        //该产品已经通过审核
        //1.判断是否通过DFile中字段DESIGN_PROCEDURE_TAG=1 工序设计 通过设计
        Boolean flag1 = idFileService.isPassProcedure(pId);
        if(!flag1){
            return new Result("该产品的工序设计或工序物料设计未完成",false);
        }

        //2.通过产品Id查询M_DESIGN_PROCEDURE中字段CHECK_TAG和DESIGN_MODULE_TAG
        Boolean aBoolean = passCheck(pId);
        if(!aBoolean){
            return new Result("该产品的工序设计或工序物料设计未完成",false);
        }
        return new Result("成功",true);
    }

    //根据产品编号查看产品工序和产品工序详细组成
    @Override
    public ProcedureDesignDto queryProcedureByPId(String pId) {
        //根据产品id查询
        MDesignProcedure dp = queryProcedureByProductId(pId);
        return queryProcedureById(dp.getId());
    }

    //通过id查询是否通过该生产工序审核和物料组成设计是否通过审核
    public Boolean passCheck(String pId){
        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();
        qw.lambda().eq(MDesignProcedure::getProductId,pId)
                .eq(MDesignProcedure::getCheckTag,"1")  //表示通过审核
                .eq(MDesignProcedure::getDesignModuleTag,"2");  //表示该工序物料设计已通过审核
        MDesignProcedure designProcedure = procedureMapper.selectOne(qw);
        if(designProcedure!=null){
            return true;
        }else{
            return false;
        }
    }

    //通过productId查看已经审核通过的工序物料设计标志已审核的产品工序
    @Override
    public MDesignProcedure queryProcedureByProductId(String pId){
        QueryWrapper<MDesignProcedure> qw = new QueryWrapper<>();
        qw.lambda().eq(MDesignProcedure::getProductId,pId)
                .eq(MDesignProcedure::getCheckTag,"1")  //表示通过审核
                .eq(MDesignProcedure::getDesignModuleTag,"2");  //表示该工序物料设计已通过审核
        return  procedureMapper.selectOne(qw);
    }

}
