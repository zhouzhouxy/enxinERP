package com.asura.enxin.controller;


import com.asura.enxin.entity.SGatherDetails;
import com.asura.enxin.service.ISGatherDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_gather`(`ID`) 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@RestController
@RequestMapping("/enxin/s-gather-details")
public class SGatherDetailsController {

    @Autowired
    private ISGatherDetailsService detailsService;

    //根据父Id查询
    @GetMapping("/query-list-by-pid")
    public ResponseEntity<List<SGatherDetails>> queryListByPId(Integer pId){
        List<SGatherDetails> list=detailsService.queryListByPId(pId);
        return ResponseEntity.ok(list);
    }
}

