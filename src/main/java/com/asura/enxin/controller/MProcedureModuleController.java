package com.asura.enxin.controller;


import com.asura.enxin.entity.dto.ProcedureAndModuleDto;
import com.asura.enxin.service.IMProcedureModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_procedure`(`ID`) 前端控制器
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/enxin/m-procedure-module")
public class MProcedureModuleController {

    @Autowired
    private IMProcedureModuleService procedureModuleService;

    @GetMapping("/queryPMByPId")
    public ResponseEntity<ProcedureAndModuleDto> queryPMByPId(Integer pId){
        try {
            ProcedureAndModuleDto dto=  procedureModuleService.queryListByPId(pId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

