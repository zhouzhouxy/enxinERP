package com.asura.enxin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.asura.enxin.entity.DFile;
import com.asura.enxin.entity.SCell;
import com.asura.enxin.entity.dto.SCellDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.SCellMapper;
import com.asura.enxin.service.IDFileService;
import com.asura.enxin.service.ISCellService;
import com.asura.enxin.service.ISGatherDetailsService;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * InnoDB free: 6144 kB 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class SCellServiceImpl extends ServiceImpl<SCellMapper, SCell> implements ISCellService {

    @Autowired
    private SCellMapper sCellMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private IDFileService fileService;

    @Autowired
    private ISGatherDetailsService isGatherDetailsService;

    //添加
    @Transactional
    @Override
    public void insert(SCell sCell,Integer fileId) {
        DFile dFile = fileService.querySimple(fileId);
        sCell.setProductId(dFile.getProductId());
        sCell.setProductName(dFile.getProductName());
        sCell.setFirstKindId(dFile.getFirstKindId());
        sCell.setFirstKindName(dFile.getFirstKindName());
        sCell.setSecondKindId(dFile.getSecondKindId());
        sCell.setSecondKindName(dFile.getSecondKindName());
        sCell.setThirdKindId(dFile.getThirdKindId());
        sCell.setThirdKindName(dFile.getThirdKindName());
        sCell.setAmount(BigDecimal.valueOf(0));
         sCell.setStoreId(idGenerator.generatorStoreId());
        sCell.setCheckTag("0");
        sCellMapper.insert(sCell);
        //修改DFile表
        fileService.updateStockTag(fileId);
    }

    //根据条件查询
    @Override
    public PageResult<SCell> querySCellByCondition(SCellDto dto) {
        QueryWrapper<SCell> qw=new QueryWrapper();
        if(!StringUtils.isEmpty(dto.getFirstKindId())){
            qw.lambda().eq(SCell::getFirstKindId,dto.getFirstKindId());
        }
        if(!StringUtils.isEmpty(dto.getSecondKindId())){
            qw.lambda().eq(SCell::getSecondKindId,dto.getSecondKindId());
        }
        if(!StringUtils.isEmpty(dto.getThirdKindId())){
            qw.lambda().eq(SCell::getThirdKindId,dto.getThirdKindId());
        }
        if(dto.getDate1()!=null){
            qw.lambda().gt(SCell::getRegisterTime, DateUtil.format(dto.getDate1(), "yyyy-MM-dd") );
        }
        if(dto.getDate2()!=null){
            qw.lambda().lt(SCell::getRegisterTime,DateUtil.format(dto.getDate2(), "yyyy-MM-dd"));
        }
        if(dto.getProductId()!=null){
            qw.lambda().eq(SCell::getProductId,dto.getProductId());
        }
        if(StringUtils.isNotBlank(dto.getCheckTag())){
             qw.lambda().eq(SCell::getCheckTag,dto.getCheckTag());
        }


        Page<SCell> dFilePage = sCellMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), qw);
        return new PageResult<>(dFilePage.getTotal(),dFilePage.getRecords());
    }


    //修改sCell实体类
    @Override
    public void updateSCell(SCell sCell) {
        sCellMapper.updateById(sCell);
    }

    //根据id查询
    @Override
    public SCell querySCellById(Integer id) {
        return sCellMapper.selectById(id);
    }

    @Override
    public SCell queryScellByProductId(String productId) {
        QueryWrapper<SCell> qw = new QueryWrapper<>();
        qw.lambda().eq(SCell::getProductId,productId);
        return sCellMapper.selectOne(qw);
    }

    @Transactional
    @Override
    public void dispatcher(Integer entryAmount, Integer gdId, Integer scellId) {
        //先查询库存单元表
        SCell sCell = sCellMapper.selectById(scellId);
        sCell.setAmount(sCell.getAmount().add(BigDecimal.valueOf(entryAmount)));
        //修改
        sCellMapper.updateById(sCell);
        //修改入库明细表
        isGatherDetailsService.updateStockTag(gdId);
    }

}
