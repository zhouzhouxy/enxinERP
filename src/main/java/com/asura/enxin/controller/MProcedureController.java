package com.asura.enxin.controller;


import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.dto.MProcedureDto;
import com.asura.enxin.entity.dto.ManufactureDto;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.IMProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //工序交接
    @GetMapping("/hand-work")
    public ResponseEntity<Result> handWork(Integer mProcedureId,Integer quliafyAmount){
        try {
            procedureService.handWork(mProcedureId,quliafyAmount);
            return ResponseEntity.ok(new Result("交接成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("交接失败",false));
        }
    }

    //工作交接复核
    @PutMapping("/hand-over-check")
    public ResponseEntity<Result> handOverCheck(@RequestBody MProcedureDto dto){
        procedureService.handOverCheck(dto.getMProcedure(),dto.getNextId());
        return ResponseEntity.ok(new Result("复核成功",true));
    }
}

