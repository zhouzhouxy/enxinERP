package com.asura.enxin.controller;


import com.asura.enxin.entity.dto.InnerProduction2Dto;
import com.asura.enxin.entity.dto.InnerProductionDto;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.IMProceduringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/enxin/m-proceduring")
public class MProceduringController {

    @Autowired
    private IMProceduringService imProceduringService;

    //内部生产登记
    @PutMapping("/add-proceduring-and-module")
    public ResponseEntity<Result> addProceduringAndModule(@RequestBody InnerProduction2Dto dto){
        try {
            System.out.println(dto);
            imProceduringService.addProceduringAndModule(dto);
            return ResponseEntity.ok(new Result("添加成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //内部生产审核
    @PutMapping("/check-proceduring-and-module")
    public ResponseEntity<Result> checkProceduringAndModule(@RequestBody InnerProductionDto dto){
        try {
            System.out.println(dto);
            imProceduringService.checkProceduringAndModule(dto);
            return ResponseEntity.ok(new Result("添加成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

