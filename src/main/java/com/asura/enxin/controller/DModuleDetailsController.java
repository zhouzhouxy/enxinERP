package com.asura.enxin.controller;


import com.asura.enxin.entity.DModuleDetails;
import com.asura.enxin.entity.dto.ModuleDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.service.IDModuleDetailsService;
import com.asura.enxin.entity.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/d_module`(`ID`) 前端控制器
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/enxin/d-module-details")
public class DModuleDetailsController {

    @Autowired
    private IDModuleDetailsService detailsService;

    //添加物料组成设计单
    @PostMapping("/addMaterialDesign")
    public ResponseEntity<Result> addMaterialDesign(@RequestBody List<ModuleDto> dto){
        System.out.println(dto);
        //添加
        try {
            detailsService.addMaterialDesign(dto);
            return ResponseEntity.ok(new Result("添加成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("添加失败",false));
        }
    }

    //根据产品id查询拥有物料
    @GetMapping("/queryMaterialByPId")
    public ResponseEntity<PageResult<DModuleDetails>> queryMaterialByPId(String productId,Integer pageNum,Integer pageSize){
        try {
            PageResult<DModuleDetails> pageResult=detailsService.queryMaterialByPId(productId,pageNum,pageSize);
            return ResponseEntity.ok(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据产品id查询所有物料
    @GetMapping("/queryMaterialByProductId")
    public ResponseEntity<List<DModuleDetails>> queryMaterialByProudId(String productId){
        List<DModuleDetails> dModuleDetails = detailsService.selectMaterialsByProductId(productId);
        //根据
        if(!CollectionUtils.isEmpty(dModuleDetails)){
            return ResponseEntity.ok(dModuleDetails);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}

