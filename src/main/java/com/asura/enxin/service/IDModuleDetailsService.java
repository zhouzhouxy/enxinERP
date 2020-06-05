package com.asura.enxin.service;

import com.asura.enxin.entity.DModuleDetails;
import com.asura.enxin.entity.DFile;
import com.asura.enxin.entity.MDesignProcedureModule;
import com.asura.enxin.entity.dto.ModuleDto;
import com.asura.enxin.entity.vo.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/d_module`(`ID`) 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IDModuleDetailsService extends IService<DModuleDetails> {

    void addMaterialDesign(List<ModuleDto> dto);

    void upDS(List<DModuleDetails> details);

    void changeDS(List<DModuleDetails> details, Integer id);

    PageResult<DModuleDetails> queryMaterialByPId(String productId,Integer pageNum,Integer pageSize);

    void updateResidualAmount(BigDecimal amount, String productId, String designId);

    void updateResidualAmount2(List<MDesignProcedureModule> list, Integer parentId);

    List<DModuleDetails> selectMaterialsByProductId(String productId);
}
