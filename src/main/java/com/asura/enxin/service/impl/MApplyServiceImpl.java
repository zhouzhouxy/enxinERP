package com.asura.enxin.service.impl;

import com.asura.enxin.entity.MApply;
import com.asura.enxin.entity.MManufacture;
import com.asura.enxin.entity.dto.ApplyConditionDto;
import com.asura.enxin.entity.dto.MApplyDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.MApplyMapper;
import com.asura.enxin.service.IMApplyService;
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
public class MApplyServiceImpl extends ServiceImpl<MApplyMapper, MApply> implements IMApplyService {

    @Autowired
    private MApplyMapper applyMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public void addApply(MApplyDto dto) {
        String s = idGenerator.generatorApplyId();

        dto.getDto().stream().forEach(item->{
            //组装mApply对象
            MApply mApply = dto.getMApply();
            mApply.setApplyId(s);
            mApply.setProductId(item.getProductId());
            mApply.setProductName(item.getProductName());
            mApply.setProductDescribe(item.getProductDescribe());
            mApply.setType(item.getType());
            mApply.setAmount(BigDecimal.valueOf(item.getAmount()));
            mApply.setCheckTag("0");
            mApply.setManufactureTag("0");
            applyMapper.insert(mApply);
        });
    }

    @Override
    public PageResult<MApply> queryApplyByCheck(Integer pageNum, Integer pageSize) {
        QueryWrapper<MApply> qw = new QueryWrapper<>();
        qw.select("distinct apply_id,  DESIGNER, REGISTER, REGISTER_TIME, remark")
                .eq("check_tag","0");

        Page<MApply> mApplyPage = applyMapper.selectPage(new Page<>(pageNum, pageSize), qw);

        return new PageResult<>(mApplyPage.getTotal(),mApplyPage.getRecords());

    }

    @Override
    public List<MApply> queryApplyByApplyId(String applyId) {
        QueryWrapper<MApply> qw = new QueryWrapper<>();
        qw.lambda().eq(MApply::getApplyId,applyId);
        return applyMapper.selectList(qw);
    }

    @Override
    public void upApply(List<MApply> applyList) {
        for (MApply mApply : applyList) {
            applyMapper.updateById(mApply);
         }
    }

    @Override
    public PageResult<MApply> queryApplyByCondition(ApplyConditionDto dto) {
        QueryWrapper<MApply> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getApplyId())){
            qw.lambda().like(MApply::getApplyId,dto.getApplyId());
        }
        if(StringUtils.isNotBlank(dto.getKeyword())){
            qw.lambda().like(MApply::getDesigner,dto.getKeyword())
                    .or().like(MApply::getProductName,dto.getKeyword())
                    .or().like(MApply::getProductDescribe,dto.getKeyword())
                    .or().like(MApply::getRemark,dto.getKeyword());
        }
        if(StringUtils.isNotBlank(dto.getState())){
            qw.lambda().eq(MApply::getCheckTag,dto.getState());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(MApply::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(MApply::getRegisterTime,dto.getDate2());
        }
        qw.select("distinct apply_id,  DESIGNER, REGISTER, REGISTER_TIME, remark,check_tag ");
        Page<MApply> mApplyPage = applyMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);

        return new PageResult<>(mApplyPage.getTotal(),mApplyPage.getRecords());
    }


    @Override
    public PageResult<MApply> queryApplyByCondition2(ApplyConditionDto dto) {
        QueryWrapper<MApply> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getApplyId())){
            qw.lambda().like(MApply::getApplyId,dto.getApplyId());
        }
        if(StringUtils.isNotBlank(dto.getKeyword())){
            qw.lambda().like(MApply::getDesigner,dto.getKeyword())
                    .or().like(MApply::getProductName,dto.getKeyword())
                    .or().like(MApply::getProductDescribe,dto.getKeyword())
                    .or().like(MApply::getRemark,dto.getKeyword());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(MApply::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().gt(MApply::getRegisterTime,dto.getDate1());
        }
        qw.lambda().eq(MApply::getManufactureTag,"0").eq(MApply::getCheckTag,"1");
        Page<MApply> mApplyPage = applyMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);

        return new PageResult<>(mApplyPage.getTotal(),mApplyPage.getRecords());
    }

    @Transactional
    @Override
    public void upManufactureTag2(String applyIdGroup) {
        applyMapper.upManufactureTag2(applyIdGroup);
    }

    //查询审核通过和没有派工的
    @Override
    public PageResult<MApply> queryAllByCheck(Integer pageNum, Integer pageSize) {
        QueryWrapper<MApply> qw = new QueryWrapper<>();
        qw.lambda().eq(MApply::getCheckTag,"1").eq(MApply::getManufactureTag,"0");
        Page<MApply> mApplyPage = applyMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        return new PageResult<>(mApplyPage.getTotal(),mApplyPage.getRecords());
    }

    //根据id批量修改派工标志
    @Transactional
    @Override
    public void upManufactureTag(String groups) {
        applyMapper.upManufactureTag(groups);
    }


}
