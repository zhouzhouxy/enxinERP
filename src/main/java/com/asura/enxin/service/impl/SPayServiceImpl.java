package com.asura.enxin.service.impl;

import com.asura.enxin.entity.SGather;
import com.asura.enxin.entity.SPay;
import com.asura.enxin.entity.dto.SGatherConditionDto;
import com.asura.enxin.entity.dto.StockDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.SPayMapper;
import com.asura.enxin.service.ISPayDetailsService;
import com.asura.enxin.service.ISPayService;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class SPayServiceImpl extends ServiceImpl<SPayMapper, SPay> implements ISPayService {

    @Autowired
    private SPayMapper payMapper;

    @Autowired
    private ISPayDetailsService isPayDetailsService;

    @Autowired
    private IdGenerator idGenerator;

    @Transactional
    @Override
    public Integer insert(SPay sPay) {
        payMapper.insert(sPay);
        return sPay.getId();
    }

    @Override
    public PageResult<SPay> queryAllSPay(Integer pageNum, Integer pageSize) {
        QueryWrapper<SPay> qw = new QueryWrapper<>();
        qw.lambda().ne(SPay::getAmountSum,"0").eq(SPay::getCheckTag,"1");
        Page<SPay> sPayPage = payMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        return new PageResult<>(sPayPage.getTotal(),sPayPage.getRecords());
    }

    @Override
    public SPay querySPayById(Integer id) {
        return payMapper.selectById(id);
    }

    //添加入库信息
    @Override
    public void addSPayAndDetails(StockDto dto) {
        SPay sPay = dto.getSPay();
        sPay.setPayId(idGenerator.generatorSpayId());
        sPay.setPaidAmountSum(BigDecimal.valueOf(0));
        sPay.setCheckTag("1");
        sPay.setPayTag("1");
        //插入
        Integer insert = insert(sPay);
        dto.getPayList().stream().forEach(item->{
            item.setParentId(insert);
            item.setPaidAmount(BigDecimal.valueOf(0));
            item.setPayTag("1");
            isPayDetailsService.insert(item);
        });
    }

    @Override
    public PageResult<SPay> querySPay(SGatherConditionDto dto) {
        QueryWrapper<SPay> qw = new QueryWrapper<>();
        qw.lambda().eq(SPay::getCheckTag,"0");
        Page<SPay> sPayPage = payMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);
        return new PageResult<>(sPayPage.getTotal(),sPayPage.getRecords());
     }

     //审核出库信息和出库详情
    @Override
    public void passCheck(SPay sPay) {
        //只用修改出库信息
        payMapper.updateById(sPay);
    }

    @Override
    public PageResult<SPay> querySPayByCondition(SGatherConditionDto dto) {
        QueryWrapper<SPay> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getSGatherId())){
            qw.lambda().like(SPay::getPayId,dto.getSGatherId());
        }
        if(StringUtils.isNotBlank(dto.getKeyWord())){
            qw.lambda().like(SPay::getStorer,dto.getKeyWord()).or()
                    .like(SPay::getReasonexact,dto.getKeyWord());
        }
        if(StringUtils.isNotBlank(dto.getCheckTag())){
            qw.lambda().eq(SPay::getCheckTag,dto.getCheckTag());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(SPay::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(SPay::getRegisterTime,dto.getDate2());
        }
        if(StringUtils.isNotBlank(dto.getCheckTag())){
            qw.lambda().eq(SPay::getCheckTag,dto.getCheckTag());
        }
        qw.lambda().gt(SPay::getAmountSum,"0");
        Page<SPay> sPayPage = payMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);
        return new PageResult<>(sPayPage.getTotal(),sPayPage.getRecords());
    }

    @Transactional
    @Override
    public void updateSpay(SPay sPay) {
        payMapper.updateById(sPay);
    }
}
