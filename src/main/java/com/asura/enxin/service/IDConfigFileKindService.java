package com.asura.enxin.service;

import com.asura.enxin.entity.DConfigFileKind;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
public interface IDConfigFileKindService extends IService<DConfigFileKind> {

    //根据父Id查询产品分类设置
    List<DConfigFileKind> queryFileKindByPId(Integer pid);

    //通过id查询KindName
    String queryKindNameById(Integer id);
}
