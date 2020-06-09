package com.asura.enxin.service.impl;

import com.asura.enxin.entity.SPay;
import com.asura.enxin.entity.SPayDetails;
import com.asura.enxin.entity.dto.SPayDto;
import com.asura.enxin.mapper.SPayDetailsMapper;
import com.asura.enxin.service.ISPayDetailsService;
import com.asura.enxin.service.ISPayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<SPayDetails> queryListByPId(Integer pId){
        QueryWrapper<SPayDetails> qw = new QueryWrapper<>();
        qw.lambda().eq(SPayDetails::getParentId,pId);
        return detailsMapper.selectList(qw);
    }

}
