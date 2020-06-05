package com.asura.enxin.service.impl;

import com.asura.enxin.entity.DConfigFileKind;
import com.asura.enxin.mapper.DConfigFileKindMapper;
import com.asura.enxin.service.IDConfigFileKindService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Service
public class DConfigFileKindServiceImpl extends ServiceImpl<DConfigFileKindMapper, DConfigFileKind> implements IDConfigFileKindService {

    @Autowired
    private DConfigFileKindMapper kindMapper;

    /**
     * 根据父Id查询产品分类配置
     * @param pid
     * @return
     */
    @Override
    public List<DConfigFileKind> queryFileKindByPId(Integer pid) {
        QueryWrapper<DConfigFileKind> qw=new QueryWrapper<>();
        qw.lambda().eq(DConfigFileKind::getPId,pid);
        return kindMapper.selectList(qw);
    }

    @Override
    public String queryKindNameById(Integer id) {
        DConfigFileKind fileKind = kindMapper.selectById(id);
        return fileKind.getKindName();
    }

}
