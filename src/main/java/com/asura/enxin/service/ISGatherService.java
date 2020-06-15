package com.asura.enxin.service;

import com.asura.enxin.entity.SGather;
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
public interface ISGatherService extends IService<SGather> {

    void addSGather(StockDto dto);

    PageResult<SGather> querySGather(SGatherConditionDto dto);

    StockDto queryById(Integer id);

    void passCheck(SGather sGather);

    void passDispatcher(Integer id);

    Integer insertSGather(SGather sGather);
}
