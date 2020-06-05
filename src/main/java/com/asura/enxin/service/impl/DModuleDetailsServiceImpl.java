package com.asura.enxin.service.impl;

import com.asura.enxin.entity.*;
import com.asura.enxin.entity.dto.ModuleDto;
import com.asura.enxin.entity.vo.PageResult;
import com.asura.enxin.mapper.DModuleDetailsMapper;
import com.asura.enxin.service.IDFileService;
import com.asura.enxin.service.IDModuleDetailsService;
import com.asura.enxin.service.IDModuleService;
import com.asura.enxin.service.IMDesignProcedureService;
import com.asura.enxin.utils.IdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/d_module`(`ID`) 服务实现类
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Service
public class DModuleDetailsServiceImpl extends ServiceImpl<DModuleDetailsMapper, DModuleDetails> implements IDModuleDetailsService {

    @Autowired
    private DModuleDetailsMapper detailsMapper;

    @Autowired
    private IDFileService idFileService;

    @Autowired
    private IDModuleService moduleService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private IMDesignProcedureService designProcedureService;

    //物料组成设计单
    @Transactional
    @Override
    public void addMaterialDesign(List<ModuleDto> dto) {
        ModuleDto md=null;
        BigDecimal subTotal=BigDecimal.ZERO;;
         for (ModuleDto moduleDto : dto) {
             if(moduleDto.getType().equals("物料")){
                 subTotal=subTotal.add(moduleDto.getSubTotal());
              }else{
                 md=moduleDto;
             }
        }
         //修改产品的工序设计状态为1
        idFileService.updateDesignProcedureTag(md);
        //插入D_MODULE表数据
        Integer integer = moduleService.insertModule(md, subTotal);
        System.out.println("productId---------》"+integer);
        for (int i = 0; i < dto.size(); i++) {
            if(dto.get(i).getType().equals("物料")){
                DModuleDetails dmd = getDModuleDetails(dto.get(i), integer,i+1);
                detailsMapper.insert(dmd);
            }
        }
    }

    //修改
    @Transactional
    @Override
    public void upDS(List<DModuleDetails> details) {
      details.stream().forEach(item->{
          item.setResidualAmount(item.getAmount());
          detailsMapper.updateById(item);
      });
    }

    @Transactional
    @Override
    public void changeDS(List<DModuleDetails> dto, Integer id) {
        //先根据商品Id删除物料细节数据
        UpdateWrapper<DModuleDetails> uw = new UpdateWrapper<>();
        uw.lambda().eq(DModuleDetails::getParentId,id);
        detailsMapper.delete(uw);
        for (int i = 0; i < dto.size(); i++) {
            //插入
            dto.get(i).setDetailsNumber(BigDecimal.valueOf(i+1));
            dto.get(i).setParentId(id);
            dto.get(i).setResidualAmount(dto.get(i).getAmount());
            detailsMapper.insert(dto.get(i));
       }
    }

    //查询通过审核的产品的物料组成
    @Override
    public PageResult<DModuleDetails> queryMaterialByPId(String productId,Integer pageNum,Integer pageSize) {
        PageResult<DModuleDetails> pageResult = new PageResult<>();
        //1.查询d_module表，通过productId可能会查出多列，循环判断是否通过审核，
        // 只会出现一个通过审核的，返回designId,再通过designId去查询产品物料细节集合
        List<DModule> list=moduleService.queryListByPId(productId);
        //2.循环判断是否有通过审核的产品
        if(list.size()==0||list==null){
            //表示这个产品没有细节，物料工序尚未完成
            pageResult.setABoolean(false);
            return pageResult;
        }
        Integer pId=null;
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            //表示已经通过审核
            if(list.get(i).getCheckTag().equals("1")){
                pId=list.get(i).getId();
            }
        }
        if(pId==null){
            //表示产品没有通过审核，或者审核没有通过
            pageResult.setABoolean(false);
            return pageResult;
        }
        //通过id对应Pid字段查询DModuleDetails表
        QueryWrapper<DModuleDetails> qw = new QueryWrapper<>();
        qw.lambda().eq(DModuleDetails::getParentId,pId);
        Page<DModuleDetails> page = detailsMapper.selectPage(new Page<>(pageNum, pageSize), qw);
        pageResult.setABoolean(true);
        pageResult.setList(page.getRecords());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }

    @Transactional
    @Override
    public void updateResidualAmount(BigDecimal amount, String productId, String designId) {
        //根据产品id查询
        DModule dModule= moduleService.selectOneByProductId(designId);

        QueryWrapper<DModuleDetails> qw = new QueryWrapper<>();
        qw.lambda().eq(DModuleDetails::getParentId,dModule.getId())
                .eq(DModuleDetails::getProductId,productId);

        DModuleDetails details = detailsMapper.selectOne(qw);

        details.setResidualAmount(details.getResidualAmount().subtract(amount));

        //根据id修改
        detailsMapper.updateById(details);
    }

    @Transactional
    @Override
    public void updateResidualAmount2(List<MDesignProcedureModule> list, Integer parentId) {
        //先查出产品生产工序
        MDesignProcedure designProcedure= designProcedureService.selectById(parentId);
        //根据工序查询出来的产品id 再查询产品物料组成
        DModule dModule= moduleService.selectOneByProductId(designProcedure.getProductId());


        //查询产品物料组成明细 修改
        for (int i = 0; i < list.size(); i++) {
            QueryWrapper<DModuleDetails> qw = new QueryWrapper<>();
            qw.lambda().eq(DModuleDetails::getParentId,dModule.getId())
                    .eq(DModuleDetails::getProductId,list.get(i).getProductId());

            DModuleDetails details = detailsMapper.selectOne(qw);


            details.setResidualAmount(details.getResidualAmount().add(list.get(i).getAmount()));

            //根据id修改
            detailsMapper.updateById(details);
        }


    }

    @Override
    public List<DModuleDetails> selectMaterialsByProductId(String productId) {
        //查询根据产品Id DMoudle
        DModule dModule = moduleService.selectOneByProductId(productId);
        return queryDModuleDetailsListByParentId(dModule.getId());
    }

    //根据父ID查询DModuleDetails
    public List<DModuleDetails> queryDModuleDetailsListByParentId(Integer parentId){
        QueryWrapper<DModuleDetails> qw = new QueryWrapper<>();
        qw.lambda().eq(DModuleDetails::getParentId,parentId);
        return detailsMapper.selectList(qw);
    }

    public DModuleDetails getDModuleDetails(ModuleDto dto,Integer pId,int i){
        DModuleDetails dmd = new DModuleDetails();
        dmd.setParentId(pId);
        dmd.setProductId(dto.getProductId());
        dmd.setDetailsNumber(BigDecimal.valueOf(i));
        dmd.setProductName(dto.getProductName());
        dmd.setType(dto.getType());
        dmd.setProductDescribe(dto.getModuleDescribe());
        dmd.setAmountUnit(dto.getAmountUnit());
        dmd.setAmount(BigDecimal.valueOf(dto.getAmount()));
        //可用数量 应该要从库存中查询
        //补上：到了后面的工序物料设计  发现这个可用数量应该在插入时就应该是用户选择的数量，方便加减
        dmd.setResidualAmount(BigDecimal.valueOf(dto.getAmount()));
        dmd.setCostPrice(dto.getCostPrice());
        dmd.setSubtotal(dto.getSubTotal());
        return dmd;
    }

    public static void main(String[] args) {
        String s1="1";
        System.out.println(s1=="1");
    }
}
