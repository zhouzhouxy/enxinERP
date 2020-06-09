package com.asura.enxin.service;

import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.dto.ManufactureDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_manufacture`(`ID`) 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMProcedureService extends IService<MProcedure> {

    void insert(MProcedure mp);

    ManufactureDto queryProcedureByPId(Integer pId);

    MProcedure queryById(Integer pId);

    List<MProcedure> selectListByPId(Integer id);

    MProcedure queryOneByOther(String workOrder, String procedureName);
}
