package com.asura.enxin.controller;


import com.asura.enxin.entity.DFile;
import com.asura.enxin.entity.dto.DFileDto;
import com.asura.enxin.entity.dto.MaterialDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.service.IDFileService;
import com.asura.enxin.entity.vo.Result;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
@RequestMapping("/enxin/d-file")
public class DFileController {

    @Autowired
    private IDFileService idFileService;

    //添加一个DFile
    @PostMapping("/add-d-file")
    public ResponseEntity<Result> addDFile(@RequestBody DFile dFile) {

        try {
            idFileService.addDFile(dFile);
            return ResponseEntity.ok(new Result("插入成功", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("插入失败", false));
        }
    }

    //查询需要复核的产品
    @GetMapping("/queryCheckDFile")
    public ResponseEntity<PageResult<DFile>> queryCheckDFile
    (@RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum
    ) {
        PageResult<DFile> dFilePageResult = idFileService.queryDFileByCheck(pageSize, pageNum);
        if (CollectionUtils.isEmpty(dFilePageResult.getList())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dFilePageResult);
    }

    //根据id查询Dfile详情
    @GetMapping("/querySimple")
    public ResponseEntity<DFile> querySimple(Integer id) {
        DFile dFile = idFileService.querySimple(id);
        if (dFile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dFile);
    }
    //点击审核通过
    @PostMapping("/passCheck")
    public ResponseEntity<Result> passCheck(@RequestBody DFile dFile){
        try {
            idFileService.passCheck(dFile);
            return ResponseEntity.ok(new Result("复核通过",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("复核失败",false));
        }
    }



    //根据条件查询Dfile
    @PostMapping("/searchDFileByCondition")
    public ResponseEntity<PageResult<DFile>> searchDFileByCondition
        (@RequestBody DFileDto dto){
        PageResult<DFile> list=idFileService.searchSearchDFileByCondition(dto.getPageSize(),dto.getPageNum(),dto);
        if(CollectionUtils.isEmpty(list.getList())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    //档案变更
    @PostMapping("/changeFile")
    public ResponseEntity<Result> changeFile(@RequestBody DFile dFile){
        try {
            idFileService.changeFile(dFile);
            return ResponseEntity.ok(new Result("档案变更成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("档案变更失败",false));
        }
    }

    //逻辑删除
    @GetMapping("/delFile")
    public ResponseEntity<Result> delFile(Integer id){
        try {
            idFileService.delFile(id);
            return ResponseEntity.ok(new Result("产品档案删除成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("产品档案删除失败",false));
        }
    }

    //恢复产品档案
    @GetMapping("/recoverFile")
    public ResponseEntity<Result> recoverFile(Integer id){
        try {
            idFileService.recoverFile(id);
            return ResponseEntity.ok(new Result("产品档案恢复成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("产品档案恢复失败",false));
        }
    }

    //永久删除产品档案
    @GetMapping("/perpetualDelFile")
    public ResponseEntity<Result> perpetualDelFile(Integer id){
        try {
            idFileService.perpetualDelFile(id);
            return ResponseEntity.ok(new Result("产品档案永久删除成功",true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Result("产品档案永久删除失败",false));
        }
    }

    //根据条件查询物料
    @PostMapping("/queryMaterial")
    public ResponseEntity<PageResult<DFile>> queryMaterial(
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestBody MaterialDto dto
    ){
        PageResult<DFile> materials=idFileService.queryMaterial(dto,dto.getPageSize(),dto.getPageNum());
        if(materials.getList()==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materials);
    }

    //根据条件查询除物料以外的产品
    @PostMapping("/queryProductByCondition")
    public ResponseEntity<PageResult<DFile>> queryProductByCondition(@RequestBody MaterialDto dto){
        PageResult<DFile> product=idFileService.queryProductByCondition(dto);
        if(product.getList()==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    //查询所有的产品为商品的类型
    @GetMapping("/queryAllGoods")
    public ResponseEntity<List<DFile>> queryAllProduct(){
            List<DFile> list=idFileService.queryAllProduct();
            return ResponseEntity.ok(list);
    }

    @PostMapping("/queryListByCondition")
    public ResponseEntity<List<DFile>> queryListByCondition(@RequestBody DFileDto dto){
        List<DFile> list= idFileService.queryListByCondition(dto);
        return ResponseEntity.ok(list);
    }

}
