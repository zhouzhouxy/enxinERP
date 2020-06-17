package com.asura.enxin.mapper;

import com.asura.enxin.entity.SGatherDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_gather`(`ID`) Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface SGatherDetailsMapper extends BaseMapper<SGatherDetails> {

    @Select("select distinct product_id from s_gather_details where gather_tag=2")
    List<String> selectProductId();

}
