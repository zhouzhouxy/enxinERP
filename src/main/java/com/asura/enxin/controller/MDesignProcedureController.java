package com.asura.enxin.controller;


import com.asura.enxin.entity.DModule;
import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.dto.ProcedureDesignDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.IDFileService;
import com.asura.enxin.service.IMDesignProcedureService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/enxin/m-design-procedure")
public class MDesignProcedureController {

    @Autowired
    private IMDesignProcedureService procedureService;

    @Autowired
    private IDFileService idFileService;

    //添加生产工序单
    @RequestMapping("/addProcedure")
    public ResponseEntity<Result> addProcedure(@RequestBody ProcedureDesignDto dto){
        System.out.println(dto);
        try {
            procedureService.addProcedure(dto);
            return ResponseEntity.ok(new Result("添加成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("添加失败",false));
        }
    }

    //查询没有审核和审核没通过的设计单
    @RequestMapping("/checkModule")
    public ResponseEntity<PageResult<MDesignProcedure>> checkModule(@RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                                                                         @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum){
        try {
            PageResult<MDesignProcedure> list=procedureService.checkProcedure(pageNum,pageSize);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("查询错误",e);
            return ResponseEntity.status(500).build();
        }
    }

    //通过id查看产品工序和产品工序详细组成
    @RequestMapping("/queryProcedureById")
    public ResponseEntity<ProcedureDesignDto> queryProcedureById(Integer id){
        try {
            ProcedureDesignDto dto=procedureService.queryProcedureById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    //通过产品id查看产品工序和产品工序详细组成
    @GetMapping("/queryProcedureByPId")
    public ResponseEntity<ProcedureDesignDto> queryProcedureById(String pId){
        try {
            ProcedureDesignDto dto=procedureService.queryProcedureByPId(pId);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    //审核
    @RequestMapping("/passCheck")
    public ResponseEntity<Result> passCheck(@RequestBody ProcedureDesignDto dto){
        try {
            procedureService.passCheck(dto);
            return ResponseEntity.ok(new Result("审核成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据条件查询
    @RequestMapping("/queryProcedureByCondition")
    public ResponseEntity<PageResult<MDesignProcedure>> queryPdByCondition(@RequestBody MaterialDto dto){
        try {
            PageResult<MDesignProcedure> result=procedureService.queryPdByCondition(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    //根据条件查询2
    @RequestMapping("/queryProcedureByCondition2")
    public ResponseEntity<PageResult<MDesignProcedure>> queryPdByCondition2(@RequestBody MaterialDto dto){
        try {
            PageResult<MDesignProcedure> result=procedureService.queryPdByCondition2(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/queryDP")
    public ResponseEntity<PageResult<MDesignProcedure>> queryDP(Integer pageNum,Integer pageSize){
        try {
            PageResult<MDesignProcedure> result = procedureService.queryDP(pageNum, pageSize);
            System.out.println(result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //分页查询需要审核的DesignProcedure
    @GetMapping("/queryDPCheck")
    public ResponseEntity<PageResult<MDesignProcedure>> queryDPCheck(Integer pageNum,Integer pageSize){
        try {
            PageResult<MDesignProcedure> result = procedureService.queryDPCheck(pageNum, pageSize);
            System.out.println(result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //修改一个物料工序
    @PostMapping("/upProcedure")
    public ResponseEntity<Result> upProcedure(@RequestBody MDesignProcedure procedure){
        try {
            procedureService.updateProcedure(procedure);
            return ResponseEntity.ok(new Result("成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    //修改一个物料工序
    @PostMapping("/upProcedure2")
    public ResponseEntity<Result> upProcedure2(@RequestBody MDesignProcedure procedure){
        try {
            procedureService.updateProcedure2(procedure);
            return ResponseEntity.ok(new Result("成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    //查询所有审核通过的商品
    @GetMapping("/queryAllGoods")
    public ResponseEntity<List<MDesignProcedure>> queryAllGoods(){
        try {
            List<MDesignProcedure> list= procedureService.selectAllGoods();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据产品编号查询该产品的工序设计或工序物料设计是否完成
    @GetMapping("/queryStateByPId")
    public ResponseEntity<Result> queryStateByPId(String pId){
        try {
            Result result = procedureService.queryStateByPId(pId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}

