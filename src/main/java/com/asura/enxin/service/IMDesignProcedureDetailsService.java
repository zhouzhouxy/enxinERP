
package com.asura.enxin.service;

import com.asura.enxin.entity.MDesignProcedureDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigDecimal;
import java.util.List;

public interface IMDesignProcedureDetailsService{
    //插入产品生产工序明细
    void addProcedureDetails(List<MDesignProcedureDetails> list, Integer id);

    List<MDesignProcedureDetails> selectListByParentId(Integer id);

    void deleteByParentId(Integer id);

    MDesignProcedureDetails selectById(Integer id);

    void update(BigDecimal subtotal,Integer id);

    void upById(Integer id);

    void confirmDel(Integer id);

    void confirmUp(Integer id);
}
