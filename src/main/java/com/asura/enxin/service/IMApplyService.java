package com.asura.enxin.service;

import com.asura.enxin.entity.MApply;
import com.asura.enxin.entity.dto.ApplyConditionDto;
import com.asura.enxin.entity.dto.MApplyDto;
import com.asura.enxin.entity.vo.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMApplyService extends IService<MApply> {

    void addApply(MApplyDto dto);

    public PageResult<MApply> queryApplyByCheck(Integer pageNum, Integer pageSize);

    List<MApply> queryApplyByApplyId(String applyId);

    void upApply(List<MApply> applyList);

    PageResult<MApply> queryApplyByCondition(ApplyConditionDto dto);

    PageResult<MApply> queryAllByCheck(Integer pageNum, Integer pageSize);

    void upManufactureTag(String groups);

    PageResult<MApply> queryApplyByCondition2(ApplyConditionDto dto);

    void upManufactureTag2(String applyIdGroup);
}
