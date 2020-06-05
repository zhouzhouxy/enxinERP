package com.asura.enxin.controller;


import com.asura.enxin.entity.DModule;
import com.asura.enxin.entity.dto.DesignDto;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.service.IDModuleDetailsService;
import com.asura.enxin.service.IDModuleService;
import com.asura.enxin.entity.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/enxin/d-module")
public class DModuleController {

    @Autowired
    private IDModuleService moduleService;

    @Autowired
    private IDModuleDetailsService detailsService;
    //查询没有审核和审核没通过的设计单
    @RequestMapping("/checkModule")
    public ResponseEntity<PageResult<DModule>> checkModule(@RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                                                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum){
        try {
            PageResult<DModule> list=moduleService.checkModule(pageNum,pageSize);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("查询错误",e);
            return ResponseEntity.status(500).build();
        }
    }

    //通过id查询设计单和物料
    @RequestMapping("/queryDesignById")
    public ResponseEntity<DesignDto> queryDesignById(Integer id){
        try {
            DesignDto dto= moduleService.queryDesignById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    //设计单审核
    @Transactional
    @PostMapping("/passCheck")
    public ResponseEntity<Result> passCheck(@RequestBody DesignDto dto){
        try {
            moduleService.upDM(dto.getDModule());
            detailsService.upDS(dto.getDetails());
            return ResponseEntity.ok(new Result("审核成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("审核失败",false));
        }
    }

    //变更设计表
    @PostMapping("/changeDesign")
    public ResponseEntity<Result> changeDesign(@RequestBody DesignDto dto){
        try {
            //
            moduleService.changeDM(dto.getDModule());
            //先删除所有的表，再重新添加进去
            detailsService.changeDS(dto.getDetails(),dto.getDModule().getId());
            return ResponseEntity.ok(new Result("变更成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("变更失败",false));
        }
    }

    //设计单查询
    @PostMapping("/queryDesignByCondition")
    public ResponseEntity<PageResult<DModule>> queryDesignByCondition(@RequestBody MaterialDto dto){
        try {
            PageResult<DModule> result = moduleService.queryDesignByCondition(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }

    }

}

