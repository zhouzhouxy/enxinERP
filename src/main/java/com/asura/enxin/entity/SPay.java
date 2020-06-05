package com.asura.enxin.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * InnoDB free: 6144 kB
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SPay implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    @TableField("PAY_ID")
    private String payId;

    @TableField("STORER")
    private String storer;

    @TableField("REASON")
    private String reason;

    @TableField("REASONEXACT")
    private String reasonexact;

    @TableField("AMOUNT_SUM")
    private BigDecimal amountSum;

    @TableField("COST_PRICE_SUM")
    private BigDecimal costPriceSum;

    @TableField("PAID_AMOUNT_SUM")
    private BigDecimal paidAmountSum;

    @TableField("REMARK")
    private String remark;

    @TableField("REGISTER")
    private String register;

    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    @TableField("CHECKER")
    private String checker;

    @TableField("CHECK_TIME")
    private LocalDateTime checkTime;

    @TableField("CHECK_TAG")
    private String checkTag;

    @TableField("ATTEMPER")
    private String attemper;

    @TableField("ATTEMPER_TIME")
    private LocalDateTime attemperTime;

    @TableField("PAY_TAG")
    private String payTag;


}
