package com.asura.enxin.service;

import com.asura.enxin.entity.SPayDetails;
import com.asura.enxin.entity.dto.SPayDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    SPayDto queryByPId(Integer pId);

    void updatePaidAmountAndPayTag(Integer outAmount, Integer sdId);

    SPayDetails queryById(Integer sdId);

    Boolean isAllPayTagPass(Integer id);

    List<String> selectProductId();
}
