package com.asura.enxin.service;

import com.asura.enxin.entity.DModule;
import com.asura.enxin.entity.dto.DesignDto;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.dto.ModuleDto;
import com.asura.enxin.entity.vo.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IDModuleService extends IService<DModule> {

    void addMaterialDesign(ModuleDto dto);

    Integer insertModule(ModuleDto item, BigDecimal subTotal);

    PageResult<DModule> checkModule(Integer pageNum,Integer pageSize);

    DesignDto queryDesignById(Integer id);

    void upDM(DModule dModule);

    PageResult<DModule> queryDesignByCondition(MaterialDto dto);

    void changeDM(DModule dModule);

    List<DModule> queryListByPId(String productId);

    DModule selectOneByProductId(String designId);
}
