package com.asura.enxin.service;

import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.MManufacture;
import com.asura.enxin.entity.dto.ApplyConditionDto;
import com.asura.enxin.entity.dto.ManufactureDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMManufactureService extends IService<MManufacture> {

    void addManufacture(MManufacture mManufacture);

    PageResult<MManufacture> queryCheck(Integer pageNum, Integer pageSize);

    ManufactureDto queryDetailById(Integer id);

    void upManufacture(MManufacture manufacture);

    PageResult<MManufacture> queryByCondtion(ApplyConditionDto dto);

    MManufacture queryManufactureById(Integer pId);

    void updateManufacture(MManufacture mManufacture);
}
