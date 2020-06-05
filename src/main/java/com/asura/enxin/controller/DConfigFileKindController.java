package com.asura.enxin.controller;


import com.asura.enxin.entity.DConfigFileKind;
import com.asura.enxin.service.IDConfigFileKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/enxin/d-config-file-kind")
public class DConfigFileKindController {

    @Autowired
    private IDConfigFileKindService kindService;

    @RequestMapping("queryFileKindByPId")
    public ResponseEntity<List<DConfigFileKind>> queryFileKindByPId(Integer pid){
        List<DConfigFileKind> dConfigFileKinds = kindService.queryFileKindByPId(pid);
        if(CollectionUtils.isEmpty(dConfigFileKinds)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dConfigFileKinds);
    }
}

