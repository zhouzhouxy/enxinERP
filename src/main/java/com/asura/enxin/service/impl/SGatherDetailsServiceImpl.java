package com.asura.enxin.service.impl;

import com.asura.enxin.entity.SGatherDetails;
import com.asura.enxin.mapper.SGatherDetailsMapper;
import com.asura.enxin.mapper.SGatherMapper;
import com.asura.enxin.service.ISGatherDetailsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_gather`(`ID`) 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class SGatherDetailsServiceImpl extends ServiceImpl<SGatherDetailsMapper, SGatherDetails> implements ISGatherDetailsService {

    @Autowired
    private SGatherDetailsMapper detailsMapper;


    //添加入库明细表
    @Transactional
    @Override
    public void addSGatherDetails(SGatherDetails item, Integer pId) {
        item.setParentId(pId);
        item.setGatherTag("1"); //表示已登记
        detailsMapper.insert(item);
    }

    @Override
    public List<SGatherDetails> queryListByPId(Integer pId) {
        QueryWrapper<SGatherDetails> qw = new QueryWrapper<>();
        qw.lambda().eq(SGatherDetails::getParentId,pId);
        return detailsMapper.selectList(qw);
    }

    //根据id修改入库标志为已调度
    @Transactional
    @Override
    public void updateStockTag(Integer gdId) {
        UpdateWrapper<SGatherDetails> uw = new UpdateWrapper<>();
        uw.lambda().set(SGatherDetails::getGatherTag,"2").eq(SGatherDetails::getId,gdId);
        detailsMapper.update(new SGatherDetails(),uw);
    }

    @Transactional
    @Override
    public void addSimpleSGatherDetails(SGatherDetails sGatherDetails) {
        detailsMapper.insert(sGatherDetails);
    }

    @Override
    public List<String> selectProductId() {
        return detailsMapper.selectProductId();
    }
}
