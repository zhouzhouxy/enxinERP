package com.asura.enxin.service;

import com.asura.enxin.entity.MProcedureModule;
import com.asura.enxin.entity.dto.ProcedureAndModuleDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_procedure`(`ID`) 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMProcedureModuleService extends IService<MProcedureModule> {

    void insert(MProcedureModule mpm);

    ProcedureAndModuleDto queryListByPId(Integer pId);

    List<MProcedureModule> selectByPId(Integer id);

    void updateRenewAmount(Integer outAmout,Integer id, String productId);

    void updateMProcedureModule(MProcedureModule mp);
}
