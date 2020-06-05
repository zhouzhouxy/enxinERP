package com.asura.enxin.controller;


import com.asura.enxin.entity.MApply;
import com.asura.enxin.entity.dto.ApplyConditionDto;
import com.asura.enxin.entity.dto.MApplyDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.IMApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 前端控制器
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/enxin/m-apply")
public class MApplyController {

    @Autowired
    private IMApplyService applyService;

    //添加
    @PostMapping("/addApply")
    public ResponseEntity<Result> addApply(@RequestBody MApplyDto dto){
        applyService.addApply(dto);
        return ResponseEntity.ok(new Result("添加成功",true));
    }

    //查询生产计划审核  通过审核的生产计划
    @GetMapping("/queryApplyByCheck")
    public ResponseEntity<PageResult<MApply>> queryApplyByCheck(Integer pageNum,Integer pageSize){
        try {
            PageResult<MApply> list=applyService.queryApplyByCheck(pageNum,pageSize);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据applyId查询详情
    @GetMapping("/queryApplyByApplyId")
    public ResponseEntity<List<MApply>> queryApplyByApplyId(String applyId){
        try {
            List<MApply> list=applyService.queryApplyByApplyId(applyId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据applyId修改
    @PutMapping("/upApply")
    public ResponseEntity<Result> upApply(@RequestBody List<MApply> applyList){
        System.out.println(applyList);
        try {
            applyService.upApply(applyList);
            return ResponseEntity.ok(new Result("修改成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("修改成功",true));
        }
    }

    //根据条件查询Apply
    @PostMapping("/queryApplyByCondition")
    public ResponseEntity<PageResult<MApply>>  queryApplyByCondition(@RequestBody ApplyConditionDto dto){
        try {
            PageResult<MApply> mApplyPageResult = applyService.queryApplyByCondition(dto);
            return ResponseEntity.ok(mApplyPageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //根据条件查询Apply
    @PostMapping("/queryApplyByCondition2")
    public ResponseEntity<PageResult<MApply>>  queryApplyByCondition2(@RequestBody ApplyConditionDto dto){
        try {
            PageResult<MApply> mApplyPageResult = applyService.queryApplyByCondition2(dto);
            return ResponseEntity.ok(mApplyPageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //查询所有的通过审核的计划
    @GetMapping("/queryAllByPassCheck")
    public ResponseEntity<PageResult<MApply>> queryAllByCheck(Integer pageNum,Integer pageSize){
        try {
            PageResult<MApply> mApplyPageResult = applyService.queryAllByCheck(pageNum,pageSize);
            return ResponseEntity.ok(mApplyPageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

