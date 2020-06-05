package com.asura.enxin.service;

import com.asura.enxin.entity.SPayDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_pay`(`ID`) 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface ISPayDetailsService extends IService<SPayDetails> {

    void insert(SPayDetails payDetails);
}
