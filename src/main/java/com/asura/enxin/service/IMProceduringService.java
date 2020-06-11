package com.asura.enxin.service;

import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.MProceduring;
import com.asura.enxin.entity.dto.InnerProduction2Dto;
import com.asura.enxin.entity.dto.InnerProductionDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_manufacture`(`ID`) 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMProceduringService extends IService<MProceduring> {

    void addProceduringAndModule(InnerProduction2Dto dto);

    MProceduring queryOneByMProcedure(MProcedure mProcedure);

    void checkProceduringAndModule(InnerProductionDto dto);
}
