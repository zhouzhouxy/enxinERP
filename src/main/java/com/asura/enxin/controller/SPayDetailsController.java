package com.asura.enxin.controller;


import com.asura.enxin.entity.dto.SPayDto;
import com.asura.enxin.service.ISPayDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_pay`(`ID`) 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@RestController
@RequestMapping("/enxin/s-pay-details")
public class SPayDetailsController {
    @Autowired
    private ISPayDetailsService detailsService;
    //根据父Id查询
    @GetMapping("/query-by-p-id")
    public ResponseEntity<SPayDto> queryByPId(Integer pId){
        SPayDto dto = detailsService.queryByPId(pId);
        return ResponseEntity.ok(dto);
    }

}

