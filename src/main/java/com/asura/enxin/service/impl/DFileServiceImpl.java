package com.asura.enxin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.asura.enxin.entity.DFile;
import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.dto.DFileDto;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.dto.ModuleDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.DFileMapper;
import com.asura.enxin.service.IDConfigFileKindService;
import com.asura.enxin.service.IDFileService;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
public class DFileServiceImpl extends ServiceImpl<DFileMapper, DFile> implements IDFileService {

    @Autowired
    private IDConfigFileKindService kindService;

    @Autowired
    private DFileMapper fileMapper;



    //往数据库中添加一个Dfile
    @Override
    public void addDFile(DFile dFile) {
        IdGenerator productIdGenerator=new IdGenerator();
        dFile.setProductId(productIdGenerator.generatorId());
        //2.通过1,2,3级id查询名称
        dFile.setFirstKindName(kindService.queryKindNameById(Integer.valueOf(dFile.getFirstKindId())));
        dFile.setSecondKindName(kindService.queryKindNameById(Integer.valueOf(dFile.getSecondKindId())));
        dFile.setThirdKindName(kindService.queryKindNameById(Integer.valueOf(dFile.getThirdKindId())));
        //3.设置审核标志check_tag=0
        dFile.setCheckTag("0");
        //4.设置删除标志 0 未删除 1 已删除
        dFile.setDeleteTag("0");
        //5.设置物料组成标志0: 未设计 1: 已设计
        dFile.setDesignModuleTag("0");
        //6.设置工序组成设计0: 未设计
        dFile.setDesignProcedureTag("0");
        //7.库存分配标志0: 未设计 1: 已设计
        dFile.setDesignCellTag("0");
        //8.设计单审核状态0：未变更 1：已变更
        dFile.setChangeTag("0");
        //9.价格变更标志 0：未变更 1：已变更
        dFile.setPriceChangeTag("0");


        //插入
        fileMapper.insert(dFile);
    }

    //分页查询所有没有通过审核以及没有被逻辑删除
    @Override
    public PageResult<DFile> queryDFileByCheck(Integer pageSize, Integer pageNum) {
        QueryWrapper<DFile> wrapper = new QueryWrapper<>();
        wrapper.lambda().ne(DFile::getCheckTag,"1").ne(DFile::getDesignProcedureTag,"1");


        QueryWrapper<DFile> qw=new QueryWrapper<>();
        qw.lambda().ne(DFile::getCheckTag,"1")      //未通过审核已经等待审核
                .eq(DFile::getDeleteTag,0);          //没有别逻辑删除

        Page<DFile> dFilePage = fileMapper.selectPage(new Page<>(pageNum, pageSize), qw);

        return new PageResult<DFile>(dFilePage.getTotal(),dFilePage.getRecords());
    }

    //根据id查询Dfile详情
    @Override
    public DFile querySimple(Integer id) {
        DFile dFile = fileMapper.selectById(id);
        if(dFile.getFileChangeAmount()==null){
            dFile.setFileChangeAmount(0);
        }
        return  dFile;
    }

    //审核通过
    @Override
    public void passCheck(DFile dFile) {
        //设置复核时间
        LocalDateTime now1 = LocalDateTime.now();
        dFile.setCheckTime(now1);
        dFile.setCheckTag("1");
        fileMapper.updateById(dFile);
    }

    @Override
    public PageResult<DFile> searchSearchDFileByCondition(Integer pageSize, Integer pageNum, DFileDto dto) {

        QueryWrapper<DFile> qw=new QueryWrapper();
        if(!StringUtils.isEmpty(dto.getFirstKindId())){
            qw.lambda().eq(DFile::getFirstKindId,dto.getFirstKindId());
        }
        if(!StringUtils.isEmpty(dto.getSecondKindId())){
            qw.lambda().eq(DFile::getSecondKindId,dto.getSecondKindId());
        }
        if(!StringUtils.isEmpty(dto.getThirdKindId())){
            qw.lambda().eq(DFile::getThirdKindId,dto.getThirdKindId());
        }
        if(!StringUtils.isEmpty(dto.getType())){
            qw.lambda().eq(DFile::getType,dto.getType());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(DFile::getRegisterTime,DateUtil.format(dto.getDate1(), "yyyy-MM-dd") );
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(DFile::getRegisterTime,DateUtil.format(dto.getDate2(), "yyyy-MM-dd"));
        }
        if(!StringUtils.isBlank(dto.getDma())){
            //物料组成标志为为设计
            qw.lambda().eq(DFile::getDesignModuleTag,dto.getDma());
        }
        if(StringUtils.isNotBlank(dto.getDpt())){
            //产品工序组成设计
            qw.lambda().eq(DFile::getDesignProcedureTag,dto.getDpt());
        }
        if(StringUtils.isNotBlank(dto.getDct())){
            //产品库存分配标志
            qw.lambda().eq(DFile::getDesignCellTag,dto.getDct());
        }
        if(StringUtils.isNotBlank(dto.getDeleteTag())){
            qw.lambda().eq(DFile::getDeleteTag,dto.getDeleteTag());
        }
        //通过审核，
        qw.lambda().eq(DFile::getCheckTag,"1");

        Page<DFile> dFilePage = fileMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        System.out.println(dFilePage.getRecords());
        return new PageResult<>(dFilePage.getTotal(),dFilePage.getRecords());
    }



    //档案变更
    @Override
    public void changeFile(DFile dFile) {
        //1.档案变更次数加一
        dFile.setFileChangeAmount(dFile.getFileChangeAmount()+1);
        //2.档案变更后需要重新审核
        dFile.setCheckTag("0");
        //如果发现其它其它后续待改
        dFile.setChangeTime(LocalDateTime.now());
        //根据Id修改
        fileMapper.updateById(dFile);
    }

    //根据id逻辑删除产品档案
    @Override
    public void delFile(Integer id) {
        DFile dFile = new DFile();
        UpdateWrapper<DFile> uw = new UpdateWrapper<>();
        uw.lambda().set(DFile::getDeleteTag,"1").eq(DFile::getId,id);

        fileMapper.update(dFile,uw);
    }

    //恢复产品档案
    @Override
    public void recoverFile(Integer id) {
        DFile dFile = new DFile();
        UpdateWrapper<DFile> uw = new UpdateWrapper<>();
        uw.lambda().set(DFile::getDeleteTag,"0").eq(DFile::getId,id);

        fileMapper.update(dFile,uw);
    }

    //永久删除产品档案
    @Override
    public void perpetualDelFile(Integer id) {
        fileMapper.deleteById(id);
    }

    //根据条件查询物料
    @Override
    public PageResult<DFile> queryMaterial(MaterialDto dto, Integer pageSize, Integer pageNum) {
        QueryWrapper<DFile> qw=new QueryWrapper();
        if(!StringUtils.isEmpty(dto.getFirstKindId())){
            qw.lambda().eq(DFile::getFirstKindId,dto.getFirstKindId());
        }
        if(!StringUtils.isEmpty(dto.getSecondKindId())){
            qw.lambda().eq(DFile::getSecondKindId,dto.getSecondKindId());
        }
        if(!StringUtils.isEmpty(dto.getThirdKindId())){
            qw.lambda().eq(DFile::getThirdKindId,dto.getThirdKindId());
        }
        //表示只查询物料 还需要审核通过
        qw.lambda().eq(DFile::getType,"物料").eq(DFile::getCheckTag,"1").eq(DFile::getDeleteTag,"0");
        //不用查询是否审核，是否删除，因为之前的操作没有对物料进行操作

        Page<DFile> dFilePage = fileMapper.selectPage(new Page<DFile>(pageNum, pageSize), qw);

        return new PageResult<>(dFilePage.getTotal(),dFilePage.getRecords());
    }

    @Transactional
    @Override
    public void updateDesignProcedureTag(ModuleDto item) {
        //根据id修改设计单状态
        DFile file=new DFile();
        UpdateWrapper<DFile> uw = new UpdateWrapper<>();
        uw.lambda().set(DFile::getDesignModuleTag,"1").eq(DFile::getId,item.getId());
        fileMapper.update(file,uw);
    }

    //根据产品id修改物料设计单状态
    @Transactional
    @Override
    public void updateDesignState(String productId) {
        DFile file=new DFile();
        UpdateWrapper<DFile> uw = new UpdateWrapper<>();
        uw.lambda().set(DFile::getDesignModuleTag,"0").eq(DFile::getProductId,productId);
        fileMapper.update(file,uw);
    }


    //根据产品id修改工序组成设计
    // DESIGN_PROCEDURE_TAG
    @Transactional
    @Override
    public void upProcedure(String productId) {
        DFile file=new DFile();
        UpdateWrapper<DFile> uw = new UpdateWrapper<>();
        uw.lambda().set(DFile::getDesignProcedureTag,"1").eq(DFile::getProductId,productId);
        fileMapper.update(file,uw);
    }
    //根据产品id修改工序组成设计
    // DESIGN_PROCEDURE_TAG
    @Transactional
    @Override
    public void upProcedureState(String productId) {
        DFile file=new DFile();
        UpdateWrapper<DFile> uw = new UpdateWrapper<>();
        uw.lambda().set(DFile::getDesignProcedureTag,"0").eq(DFile::getProductId,productId);
        fileMapper.update(file,uw);
    }

    //查询工序组成标志为已设计
    @Override
    public PageResult<MDesignProcedure> checkProcedure(Integer pageNum, Integer pageSize) {
        QueryWrapper<DFile> qw = new QueryWrapper<>();
//        qw.lambda().eq(DFile::)
        return null;
    }

    @Override
    public PageResult<DFile> queryProductByCondition(MaterialDto dto) {
        QueryWrapper<DFile> qw = qw(dto);
        qw.lambda().ne(DFile::getType,"物料").eq(DFile::getCheckTag,"1").eq(DFile::getDeleteTag,"0");
        //不用查询是否审核，是否删除，因为之前的操作没有对物料进行操作
        Page<DFile> dFilePage = fileMapper.selectPage(new Page<DFile>(dto.getPageNum(), dto.getPageSize()), qw);

        return new PageResult<>(dFilePage.getTotal(),dFilePage.getRecords());
    }

    //根据pId查询是否通过工序设计
    @Override
    public Boolean isPassProcedure(String pId) {
        QueryWrapper<DFile> qw = new QueryWrapper<>();
        qw.lambda().eq(DFile::getProductId,pId).eq(DFile::getDesignProcedureTag,"1");
        DFile dFile = fileMapper.selectOne(qw);
        if(dFile!=null){
            return true;
        }else{
            return false;
        }
    }

    //修改库存状态
    @Transactional
    @Override
    public void updateStockTag(Integer id) {
        UpdateWrapper<DFile> uw = new UpdateWrapper<>();
        uw.lambda().set(DFile::getDesignCellTag,"1").eq(DFile::getId,id);
        fileMapper.update(new DFile(),uw);
    }

    @Override
    public List<DFile> queryListByCondition(DFileDto dto) {
        QueryWrapper<DFile> qw=new QueryWrapper();
        if(!StringUtils.isEmpty(dto.getFirstKindId())){
            qw.lambda().eq(DFile::getFirstKindId,dto.getFirstKindId());
        }
        if(!StringUtils.isEmpty(dto.getSecondKindId())){
            qw.lambda().eq(DFile::getSecondKindId,dto.getSecondKindId());
        }
        if(!StringUtils.isEmpty(dto.getThirdKindId())){
            qw.lambda().eq(DFile::getThirdKindId,dto.getThirdKindId());
        }
        qw.lambda().eq(DFile::getCheckTag,"1");
        return fileMapper.selectList(qw);
    }

    @Override
    public List<DFile> queryAllProduct() {
        QueryWrapper<DFile> qw = new QueryWrapper<>();
        qw.lambda().eq(DFile::getType,"商品").eq(DFile::getDeleteTag,"0");
        return fileMapper.selectList(qw);
    }


    //根据条件查询产品
    public QueryWrapper<DFile> qw(MaterialDto dto){
        QueryWrapper<DFile> qw=new QueryWrapper();
        if(!StringUtils.isEmpty(dto.getFirstKindId())){
            qw.lambda().eq(DFile::getFirstKindId,dto.getFirstKindId());
        }
        if(!StringUtils.isEmpty(dto.getSecondKindId())){
            qw.lambda().eq(DFile::getSecondKindId,dto.getSecondKindId());
        }
        if(!StringUtils.isEmpty(dto.getThirdKindId())){
            qw.lambda().eq(DFile::getThirdKindId,dto.getThirdKindId());
        }
        //表示只查询物料 还需要审核通过
        return qw;
    }


    //

}
