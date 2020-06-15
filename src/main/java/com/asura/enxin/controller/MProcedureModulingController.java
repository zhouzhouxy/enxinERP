package com.asura.enxin.controller;


import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.MProcedureModuling;
import com.asura.enxin.entity.dto.InnerProductionDto;
import com.asura.enxin.entity.dto.MProcedureModulingDto;
import com.asura.enxin.service.IMProcedureModulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_proceduring`(`ID`) 前端控制器
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/enxin/m-procedure-moduling")
public class MProcedureModulingController {

    @Autowired
    private IMProcedureModulingService imProcedureModulingService;

    //通过工序查询物料
    @PostMapping("/query-proceduring-and-moduling")
    public ResponseEntity<InnerProductionDto> queryProceduringAndModuling(@RequestBody MProcedure mProcedure){
        try {
            InnerProductionDto dto=imProcedureModulingService.queryProceduringAndModuling(mProcedure);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据工序id查询生产工序物料过程记录
    @GetMapping("/query-procedure-moduling-by-pid")
    public ResponseEntity<List<MProcedureModulingDto>> queryProcedureModuling(Integer id){
        List<MProcedureModulingDto> list = imProcedureModulingService.queryProcedureModulingByPId(id);
        return ResponseEntity.ok(list);
    }
}

