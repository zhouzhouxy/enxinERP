package com.asura.enxin.service;

import com.asura.enxin.entity.MDesignProcedureModule;
import com.asura.enxin.entity.MProcedureModule;
import com.asura.enxin.entity.dto.ProcedureDetailDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_design_procedure_details`(`ID 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMDesignProcedureModuleService extends IService<MDesignProcedureModule> {

    void upProcedure(ProcedureDetailDto dto);

    List<MDesignProcedureModule> selectByPId(Integer pId);

    void delByPId(Integer id);

    void insert(MProcedureModule mpm);
}
