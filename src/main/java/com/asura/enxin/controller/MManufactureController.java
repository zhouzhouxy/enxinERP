package com.asura.enxin.controller;


import com.asura.enxin.entity.MManufacture;
import com.asura.enxin.entity.dto.ApplyConditionDto;
import com.asura.enxin.entity.dto.ManufactureDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.IMManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * InnoDB free: 6144 kB 前端控制器
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/enxin/m-manufacture")
public class MManufactureController {

    @Autowired
    private IMManufactureService imManufactureService;

    //添加生产总表
    @PutMapping("/addManufacture")
    public ResponseEntity<Result> addManufacture(@RequestBody MManufacture mManufacture){
        try {
            imManufactureService.addManufacture(mManufacture);
            return ResponseEntity.ok(new Result("添加成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("添加失败",false));
        }
    }

    //查询待审核的生产总表
    @GetMapping("/queryCheck")
    public ResponseEntity<PageResult<MManufacture>> queryCheck(Integer pageNum,Integer pageSize){
        try {
            PageResult<MManufacture> pageResult = imManufactureService.queryCheck(pageNum, pageSize);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据生产id查询详情
    @GetMapping("/queryDetailById")
    public ResponseEntity<ManufactureDto> queryDetailById(Integer id){
        try {
            ManufactureDto manufactureDto = imManufactureService.queryDetailById(id);
            return ResponseEntity.ok(manufactureDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //修改
    @PutMapping("/upManufacture")
    public ResponseEntity<Result> upManufacture(@RequestBody MManufacture manufacture){
        try {
            imManufactureService.upManufacture(manufacture);
            return ResponseEntity.ok(new Result("修改成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("修改失败",false));
        }
    }

    @PostMapping("/queryByCondition")
    public ResponseEntity<PageResult<MManufacture>> queryByCondition(@RequestBody ApplyConditionDto dto){
        try {
            PageResult<MManufacture> pageResult= imManufactureService.queryByCondtion(dto);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

