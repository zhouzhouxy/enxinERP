package com.asura.enxin.service;

import com.asura.enxin.entity.DFile;
import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.dto.DFileDto;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.dto.ModuleDto;
import com.asura.enxin.entity.vo.PageResult;
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
public interface IDFileService extends IService<DFile> {

    void addDFile(DFile dFile);

    PageResult<DFile> queryDFileByCheck(Integer pageSize, Integer pageNum);

    //根据id查看详情
    DFile querySimple(Integer id);

    //产品通过复核
    void passCheck(DFile dFile);

    //根据条件查询Dfile
    PageResult<DFile> searchSearchDFileByCondition(Integer pageSize, Integer pageNum, DFileDto dto);

    //变更产品
    void changeFile(DFile dFile);

    //逻辑删除档案
    void delFile(Integer id);

    //恢复档案
    void recoverFile(Integer id);

    //永久删除档案
    void perpetualDelFile(Integer id);

    //根据条件查询物料
    PageResult<DFile> queryMaterial(MaterialDto dto, Integer pageSize, Integer pageNum);

    void updateDesignProcedureTag(ModuleDto item);

    void updateDesignState(String productId);

    List<DFile> queryAllProduct();

    void upProcedure(String productId);

    void upProcedureState(String productId);

    PageResult<MDesignProcedure> checkProcedure(Integer pageNum, Integer pageSize);

    PageResult<DFile> queryProductByCondition(MaterialDto dto);

    Boolean isPassProcedure(String pId);
}
