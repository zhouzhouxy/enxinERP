package com.asura.enxin.controller;

import com.asura.enxin.service.IDConfigPublicCharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/26/026 17:52
 */
@CrossOrigin
@RestController
@RequestMapping("publicChar")
public class DConfigPublicCharController {

    @Autowired
    private IDConfigPublicCharService charService;

    //查询档次和级别
    @RequestMapping("/queryGradeAndUseType")
    public ResponseEntity<Map<String, List<String>>> queryGradeAndUseType(){
        List<String>  grade= charService.queryGrade();
        List<String> useType = charService.queryUseType();
        if(CollectionUtils.isEmpty(grade)&&CollectionUtils.isEmpty(useType)){
            return ResponseEntity.notFound().build();
        }
        Map<String, List<String>> map = new HashMap<>();
        map.put("grade",grade);
        map.put("useType",useType);
        return ResponseEntity.ok(map);

    }

}
