package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.DFile;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/29/029 10:05
 */
@Data
public class ModuleDto extends DFile {


    private Integer amount;

    private BigDecimal subTotal;

    private String designer;

    private LocalDateTime registerTime2;

    private String moduleDescribe;

    @Override
    public String toString() {
        return super.toString()+"ModuleDto{" +
                "amount=" + amount +
                ", subTotal=" + subTotal +
                ", designMan='" + designer + '\'' +
                ", registerTime=" + registerTime2 +
                ", moduleDescribe='" + moduleDescribe + '\'' +
                '}';
    }
}
