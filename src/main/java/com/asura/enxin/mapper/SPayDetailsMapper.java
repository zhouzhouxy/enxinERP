package com.asura.enxin.mapper;

import com.asura.enxin.entity.SPayDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_pay`(`ID`) Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface SPayDetailsMapper extends BaseMapper<SPayDetails> {

    @Select("select distinct PRODUCT_ID from s_pay_details")
    List<String> selectProductId();

}
