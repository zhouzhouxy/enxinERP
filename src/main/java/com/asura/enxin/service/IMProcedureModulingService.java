package com.asura.enxin.service;

import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.MProcedureModule;
import com.asura.enxin.entity.MProcedureModuling;
import com.asura.enxin.entity.dto.InnerProcedureModuleDto;
import com.asura.enxin.entity.dto.InnerProductionDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_proceduring`(`ID`) 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMProcedureModulingService extends IService<MProcedureModuling> {

    void addProcedureModuling(MProcedureModuling mProcedureModuling);

    InnerProductionDto queryProceduringAndModuling(MProcedure mProcedure);

    void updateProcedureModuling(Integer id, String productId, BigDecimal realAmount, Integer thisAmount);

    List<MProcedureModuling> queryProcedureModuling(Integer id);
}
