package com.asura.enxin.controller;


import com.asura.enxin.entity.SCell;
import com.asura.enxin.entity.dto.SCellDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.ISCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * InnoDB free: 6144 kB 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@RestController
@RequestMapping("/enxin/s-cell")
public class SCellController {

    @Autowired
    private ISCellService isCellService;

    @PutMapping("/addScell")
    public ResponseEntity<Result> addScell(@RequestBody SCellDto dto ){
        try {
            isCellService.insert(dto.getSCell(),dto.getFileId());
            return ResponseEntity.ok(new Result("添加成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("添加失败",false));
        }
    }

    //根据条件分页查询安全库存配置单
    @PostMapping("/queryScellByCondition")
    public ResponseEntity<PageResult<SCell>> querySCellByCondition(@RequestBody SCellDto dto){
        try {
            PageResult<SCell> list= isCellService.querySCellByCondition(dto);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //修改 复核通过，和变更
    @PutMapping("/updateSCell")
    public ResponseEntity<Result> updateSCell(@RequestBody SCell sCell){
        try {
            isCellService.updateSCell(sCell);
            return ResponseEntity.ok(new Result("修改成功",true));
        } catch (Exception e) {
            return ResponseEntity.ok(new Result("修改失败",true));
        }
    }

    //根据Id查询单个
    @GetMapping("/querySCellById")
    public ResponseEntity<SCell> querySCellById(Integer id){
        try {
            SCell sCell=isCellService.querySCellById(id);
            return ResponseEntity.ok(sCell);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //根据id查询
    @GetMapping("/query-s-cell-by-productId")
    public ResponseEntity<SCell> querySCellByProductId(String productId){
        try {
        SCell sCell=isCellService.queryScellByProductId(productId);
        return ResponseEntity.ok(sCell);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }

    //入库调度
    @GetMapping("/dispatcher")
    public ResponseEntity<Result> dispatcher(Integer entryAmount,Integer gdId,Integer scellId){
        try {
            isCellService.dispatcher(entryAmount,gdId,scellId);
            return ResponseEntity.ok(new Result("调度成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //出库调度
    @GetMapping("/out-dispatcher")
    public ResponseEntity<Result> outDispatcher(Integer outAmount,Integer sdId,Integer sCellId){
        try {
            isCellService.outDispatcher(outAmount,sdId,sCellId);
            return ResponseEntity.ok(new Result("出库调度成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

