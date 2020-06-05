package com.asura.enxin.service.impl;

import com.asura.enxin.entity.SPayDetails;
import com.asura.enxin.mapper.SPayDetailsMapper;
import com.asura.enxin.service.ISPayDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void insert(SPayDetails payDetails) {
        detailsMapper.insert(payDetails);
    }
}
