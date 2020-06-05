package com.asura.enxin.service;

import com.asura.enxin.entity.DModule;
import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.dto.ProcedureDesignDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IMDesignProcedureService extends IService<MDesignProcedure> {

    void addProcedure(ProcedureDesignDto dto);

    PageResult<MDesignProcedure> checkProcedure(Integer pageNum, Integer pageSize);

    ProcedureDesignDto queryProcedureById(Integer id);

    void passCheck(ProcedureDesignDto dto);

    PageResult<MDesignProcedure> queryPdByCondition(MaterialDto dto);

    PageResult<MDesignProcedure> queryDP(Integer pageNum, Integer pageSize);

    MDesignProcedure selectById(Integer parentId);

    void updateProcedure(MDesignProcedure procedure);

    PageResult<MDesignProcedure> queryDPCheck(Integer pageNum, Integer pageSize);

    void updateProcedure2(MDesignProcedure procedure);

    List<MDesignProcedure> selectAllGoods();

    PageResult<MDesignProcedure> queryPdByCondition2(MaterialDto dto);


    void updateProcedureMTeById(Integer parentId, String s);

    Result queryStateByPId(String pId);

    ProcedureDesignDto queryProcedureByPId(String pId);

    MDesignProcedure queryProcedureByProductId(String pId);
}
