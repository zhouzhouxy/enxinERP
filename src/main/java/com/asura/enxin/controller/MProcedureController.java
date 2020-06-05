package com.asura.enxin.controller;


import com.asura.enxin.entity.dto.ManufactureDto;
import com.asura.enxin.service.IMProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_manufacture`(`ID`) 前端控制器
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/enxin/m-procedure")
public class MProcedureController {

    @Autowired
    private IMProcedureService procedureService;

    @RequestMapping("/queryProcedureByPId")
    public ResponseEntity<ManufactureDto> queryProcedureByPId(Integer pId){
        try {
            ManufactureDto dto =procedureService.queryProcedureByPId(pId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

