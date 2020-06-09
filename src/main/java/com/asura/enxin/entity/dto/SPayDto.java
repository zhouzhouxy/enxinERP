package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.SPay;
import com.asura.enxin.entity.SPayDetails;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/7/007 22:58
 */
@Data
public class SPayDto implements Serializable {
    private SPay sPay;
    private List<SPayDetails> list;
}
