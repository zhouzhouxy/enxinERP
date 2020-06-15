package com.asura.enxin.service.impl;

import com.asura.enxin.entity.SPay;
import com.asura.enxin.entity.SPayDetails;
import com.asura.enxin.entity.dto.SPayDto;
import com.asura.enxin.mapper.SPayDetailsMapper;
import com.asura.enxin.service.ISPayDetailsService;
import com.asura.enxin.service.ISPayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_pay`(`ID`) 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class SPayDetailsServiceImpl extends ServiceImpl<SPayDetailsMapper, SPayDetails> implements ISPayDetailsService {

    @Autowired
    private SPayDetailsMapper detailsMapper;

    @Autowired
    private ISPayService isPayService;

    @Transactional
    @Override
    public void insert(SPayDetails payDetails) {
        detailsMapper.insert(payDetails);
    }

    @Override
    public SPayDto queryByPId(Integer pId) {
        SPayDto dto = new SPayDto();
        //查询出库
        SPay sPay = isPayService.querySPayById(pId);
        //查询出库详情
        List<SPayDetails> list = queryListByPId(pId);

        dto.setSPay(sPay);
        dto.setList(list);
        return dto;
    }

    //修改出库标志和确认出库件数
    @Transactional
    @Override
    public void updatePaidAmountAndPayTag(Integer outAmount, Integer sdId) {
        UpdateWrapper<SPayDetails> uw = new UpdateWrapper<>();
        uw.lambda().set(SPayDetails::getPaidAmount, BigDecimal.valueOf(outAmount))  //确认出库件数
                    .set(SPayDetails::getPayTag,"2")    //表示已调度
                .eq(SPayDetails::getId,sdId);
        detailsMapper.update(new SPayDetails(),uw);
    }

    @Override
    public SPayDetails queryById(Integer sdId) {
        return detailsMapper.selectById(sdId);
    }

    //通过父id判断是否都已经被调度了
    @Override
    public Boolean isAllPayTagPass(Integer id) {
        List<SPayDetails> list = queryListByPId(id);
        AtomicReference<Boolean> flag= new AtomicReference<>(true);
        list.forEach(item->{
            if(item.getPayTag().equals("1")){
                flag.set(false);
            }
        });
        return flag.get();
    }

    @Override
    public List<String> selectProductId() {
        return detailsMapper.selectProductId();
    }

    public List<SPayDetails> queryListByPId(Integer pId){
        QueryWrapper<SPayDetails> qw = new QueryWrapper<>();
        qw.lambda().eq(SPayDetails::getParentId,pId);
        return detailsMapper.selectList(qw);
    }

}
