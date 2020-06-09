package com.asura.enxin.service;

import com.asura.enxin.entity.SGatherDetails;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_gather`(`ID`) 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface ISGatherDetailsService extends IService<SGatherDetails> {

    void addSGatherDetails(SGatherDetails item, Integer pId);

    List<SGatherDetails> queryListByPId(Integer pId);

    void updateStockTag(Integer gdId);
}
