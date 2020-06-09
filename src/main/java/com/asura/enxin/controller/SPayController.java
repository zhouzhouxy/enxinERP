package com.asura.enxin.controller;


import com.asura.enxin.entity.SPay;
import com.asura.enxin.entity.dto.SGatherConditionDto;
import com.asura.enxin.entity.dto.StockDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.ISPayService;
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
@RequestMapping("/enxin/s-pay")
public class SPayController {

    @Autowired
    private ISPayService isPayService;

    @GetMapping("/query-all-s-pay")
    public ResponseEntity<PageResult<SPay>> queryAllSPay(Integer pageNum, Integer pageSize){
        PageResult<SPay> payPageResult=isPayService.queryAllSPay(pageNum,pageSize);
        return ResponseEntity.ok(payPageResult);
    }

    @PostMapping("/query-s-pay")
    public ResponseEntity<PageResult<SPay>> querySPay(@RequestBody SGatherConditionDto dto){
        PageResult<SPay> payPageResult=isPayService.querySPay(dto);
        return ResponseEntity.ok(payPageResult);
    }

    //根据条件查询出库单
    @PostMapping("query-s-pay-by-condition")
    public ResponseEntity<PageResult<SPay>> querySPayByCondition(@RequestBody SGatherConditionDto dto){
        PageResult<SPay> payPageResult=isPayService.querySPayByCondition(dto);
        return ResponseEntity.ok(payPageResult);
    }


    //添加入库和入库详细
    @PutMapping("/add-s-pay")
    public ResponseEntity<Result> addSPay(@RequestBody StockDto dto){
        try {
            isPayService.addSPayAndDetails(dto);
            return ResponseEntity.ok(new Result("添加成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("添加失败",false));
        }
    }

    //审核
    @PutMapping("/pass-check")
    public ResponseEntity<Result> passCheck(@RequestBody SPay sPay){
        try {
            isPayService.passCheck(sPay);
            return ResponseEntity.ok(new Result("审核成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("审核失败",false));
        }
    }


}

