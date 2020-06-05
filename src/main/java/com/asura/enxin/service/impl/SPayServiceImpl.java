package com.asura.enxin.service.impl;

import com.asura.enxin.entity.SPay;
import com.asura.enxin.mapper.SPayMapper;
import com.asura.enxin.service.ISPayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Integer insert(SPay sPay) {
        payMapper.insert(sPay);
        return sPay.getId();
    }
}
