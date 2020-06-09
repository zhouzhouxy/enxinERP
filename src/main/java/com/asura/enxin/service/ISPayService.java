package com.asura.enxin.service;

import com.asura.enxin.entity.SPay;
import com.asura.enxin.entity.dto.SGatherConditionDto;
import com.asura.enxin.entity.dto.StockDto;
import com.asura.enxin.entity.vo.PageResult;
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

    PageResult<SPay> queryAllSPay(Integer pageNum, Integer pageSize);

    SPay querySPayById(Integer id);

    void addSPayAndDetails(StockDto dto);

    PageResult<SPay> querySPay(SGatherConditionDto dto);

    void passCheck(SPay sPay);

    PageResult<SPay> querySPayByCondition(SGatherConditionDto dto);
}
