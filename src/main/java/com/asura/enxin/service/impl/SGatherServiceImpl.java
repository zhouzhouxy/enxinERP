package com.asura.enxin.service.impl;

import com.asura.enxin.entity.SGather;
import com.asura.enxin.entity.SGatherDetails;
import com.asura.enxin.entity.dto.SGatherConditionDto;
import com.asura.enxin.entity.dto.StockDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.SGatherMapper;
import com.asura.enxin.service.ISGatherDetailsService;
import com.asura.enxin.service.ISGatherService;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class SGatherServiceImpl extends ServiceImpl<SGatherMapper, SGather> implements ISGatherService {

    @Autowired
    private SGatherMapper gatherMapper;

    @Autowired
    private ISGatherDetailsService detailsService;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public void addSGather(StockDto dto) {
        SGather sGather = dto.getSGather();
        sGather.setGatherId(idGenerator.generatorGatherId());
        sGather.setCheckTag("0");
        sGather.setGatherTag("1");
        Integer pId = insertGather(sGather);
        //循环添加入库明细表
        dto.getList().stream().forEach(item->{
            detailsService.addSGatherDetails(item,pId);
        });
    }

    @Override
    public PageResult<SGather> querySGather(SGatherConditionDto dto) {
        QueryWrapper<SGather> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(dto.getSGatherId())){
            qw.lambda().like(SGather::getGatherId,dto.getSGatherId());
        }
        if(StringUtils.isNotBlank(dto.getKeyWord())){
            qw.lambda().like(SGather::getStorer,dto.getKeyWord()).or()
                    .like(SGather::getReasonexact,dto.getKeyWord());
        }
        if(StringUtils.isNotBlank(dto.getCheckTag())){
            qw.lambda().eq(SGather::getCheckTag,dto.getCheckTag());
        }
        if(dto.getDate1()!=null){
           qw.lambda().gt(SGather::getRegisterTime,dto.getDate1());
        }
        if(dto.getDate2()!=null){
            qw.lambda().gt(SGather::getRegisterTime,dto.getDate2());
        }
        //只查询入库标志为已登记的，不查询已调度
        qw.lambda().eq(SGather::getGatherTag,"1");
        Page<SGather> sGatherPage = gatherMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);

        return new PageResult<>(sGatherPage.getTotal(),sGatherPage.getRecords());
    }

    //根据id查询
    @Override
    public StockDto queryById(Integer id) {
        SGather sGather = gatherMapper.selectById(id);
        //根据id查询SGatherDetails
        List<SGatherDetails> list = detailsService.queryListByPId(id);
        StockDto dto = new StockDto();
        dto.setList(list);
        dto.setSGather(sGather);
        return dto;
    }

    //修改
    @Override
    public void passCheck(SGather sGather) {
        gatherMapper.updateById(sGather);
    }

    @Override
    public void passDispatcher(Integer id) {
        UpdateWrapper<SGather> uw = new UpdateWrapper<>();
        uw.lambda().set(SGather::getGatherTag,"2").eq(SGather::getId,id);
        gatherMapper.update(new SGather(),uw);
    }

    //添加
    public Integer insertGather(SGather gather){
        gatherMapper.insert(gather);
        return gather.getId();
    }
}
