package com.asura.enxin.controller;


import com.asura.enxin.entity.MDesignProcedureDetails;
import com.asura.enxin.entity.MDesignProcedureModule;
import com.asura.enxin.entity.dto.ProcedureDetailDto;
import com.asura.enxin.entity.dto.ProcedureModuleDetailsDto;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.IMDesignProcedureDetailsService;
import com.asura.enxin.service.IMDesignProcedureModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_design_procedure`(`ID`) 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/enxin/m-design-procedure-details")
public class MDesignProcedureDetailsController {

    @Autowired
    private IMDesignProcedureModuleService moduleService;


    @Autowired
    private IMDesignProcedureDetailsService detailsService;

    //修改该工序状态和添加对应的物料设计
    @PostMapping("/upProcedure")
    public ResponseEntity<Result> upProcedure(@RequestBody ProcedureDetailDto dto){
        try {
            moduleService.upProcedure(dto);
            return ResponseEntity.ok(new Result("操作成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("操作失败",false));
        }
    }

    //根据id查询工序和工序物料
    @GetMapping("/queryProcedureDetails")
    public ResponseEntity<ProcedureModuleDetailsDto> queryProcedureDetails(Integer pId){
        try {
            List<MDesignProcedureModule> list=moduleService.selectByPId(pId);
            MDesignProcedureDetails details= detailsService.selectById(pId);
            ProcedureModuleDetailsDto pmdd = new ProcedureModuleDetailsDto();
            pmdd.setDetails(details);
            pmdd.setList(list);
            return ResponseEntity.ok(pmdd);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Transactional
    @GetMapping("/confirmDel")
    public ResponseEntity<Result> confirmDel(Integer id){
        try {
            detailsService.confirmDel(id);
            return ResponseEntity.ok(new Result("删除成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("删除失败",false));
        }
    }


    @Transactional
    @GetMapping("/confirmUp")
    public ResponseEntity<Result> confirmUp(Integer id){
        try {
            detailsService.confirmUp(id);
            return ResponseEntity.ok(new Result("修改成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("修改失败",false));
        }
    }
}

