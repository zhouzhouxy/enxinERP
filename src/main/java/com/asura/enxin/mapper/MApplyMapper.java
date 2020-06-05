package com.asura.enxin.mapper;

import com.asura.enxin.entity.MApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB Mapper 接口
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface MApplyMapper extends BaseMapper<MApply> {

    @Update("update m_apply set MANUFACTURE_TAG=1 where id in(${groups})")
    void upManufactureTag(String groups);

    @Update("update m_apply set MANUFACTURE_TAG=0 where id in(${groups})")
    void upManufactureTag2(String applyIdGroup);

}
