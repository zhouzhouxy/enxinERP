package com.asura.enxin.service;

import com.asura.enxin.entity.SPay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface ISPayService extends IService<SPay> {

    Integer insert(SPay sPay);
}
