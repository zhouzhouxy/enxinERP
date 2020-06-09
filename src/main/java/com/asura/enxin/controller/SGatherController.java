package com.asura.enxin.controller;


import com.asura.enxin.entity.SGather;
import com.asura.enxin.entity.dto.SGatherConditionDto;
import com.asura.enxin.entity.dto.StockDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.entity.vo.Result;
import com.asura.enxin.service.ISGatherService;
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
@RequestMapping("/enxin/s-gather")
public class SGatherController {

    @Autowired
    private ISGatherService isGatherService;

    //添加入库表和入库明细表
    @PutMapping("/add-s-gather")
    public ResponseEntity<Result> addSGather(@RequestBody StockDto dto){
      try {
          isGatherService.addSGather(dto);
          return ResponseEntity.ok(new Result("添加成功",true));
      }catch (Exception e){
          e.printStackTrace();
          return ResponseEntity.ok(new Result("添加失败",false));
      }
    }

    //条件查询入库表
    @PostMapping("/query-s-gather")
    public ResponseEntity<PageResult<SGather>> querySGather(@RequestBody SGatherConditionDto dto){
        PageResult<SGather> pageResult=isGatherService.querySGather(dto);
        return ResponseEntity.ok(pageResult);
    }

    //通过Id查询sGather和sGatherDetails
    @GetMapping("/query-by-id")
    public ResponseEntity<StockDto> queryById(Integer id){
        StockDto dto= isGatherService.queryById(id);
        return ResponseEntity.ok(dto);
    }

    //审核
    @PutMapping("/pass-check")
    public ResponseEntity<Result> passCheck(@RequestBody SGather sGather){
        try{
            isGatherService.passCheck(sGather);
            return ResponseEntity.ok(new Result("审核成功",true));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pass-dispatcher")
    public ResponseEntity<Void> passDispatcher(Integer id){
        try {
            isGatherService.passDispatcher(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

