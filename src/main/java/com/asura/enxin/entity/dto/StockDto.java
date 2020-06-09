package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.SGather;
import com.asura.enxin.entity.SGatherDetails;
import com.asura.enxin.entity.SPay;
import com.asura.enxin.entity.SPayDetails;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/6/006 11:45
 */
@Data
public class StockDto implements Serializable {
    private SGather sGather;
    private SPay sPay;
    private List<SGatherDetails> list;
    private List<SPayDetails> payList;
}
