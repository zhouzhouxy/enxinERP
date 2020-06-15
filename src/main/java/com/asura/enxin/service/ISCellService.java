package com.asura.enxin.service;

import com.asura.enxin.entity.SCell;
import com.asura.enxin.entity.dto.SCellDto;
import com.asura.enxin.entity.vo.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 6144 kB 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface ISCellService extends IService<SCell> {

    void insert(SCell sCell,Integer fileId);

    PageResult<SCell> querySCellByCondition(SCellDto dto);

    void updateSCell(SCell sCell);

    SCell querySCellById(Integer id);

    SCell queryScellByProductId(String productId);

    void dispatcher(Integer entryAmount, Integer gdId, Integer scellId);

    void outDispatcher(Integer outAmount, Integer sdId, Integer sCellId);

    PageResult<SCell> dynamicStockQuery(SCellDto dto);
}
