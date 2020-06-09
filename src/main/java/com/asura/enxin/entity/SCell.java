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
public class SCell implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    @TableField("STORE_ID")
    private String storeId;

    @TableField("PRODUCT_ID")
    private String productId;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("FIRST_KIND_ID")
    private String firstKindId;

    @TableField("FIRST_KIND_NAME")
    private String firstKindName;

    @TableField("SECOND_KIND_ID")
    private String secondKindId;

    @TableField("SECOND_KIND_NAME")
    private String secondKindName;

    @TableField("THIRD_KIND_ID")
    private String thirdKindId;

    @TableField("THIRD_KIND_NAME")
    private String thirdKindName;

    @TableField("MIN_AMOUNT")
    private BigDecimal minAmount;

    @TableField("MAX_AMOUNT")
    private BigDecimal maxAmount;

    @TableField("MAX_CAPACITY_AMOUNT")
    private BigDecimal maxCapacityAmount;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("CONFIG")
    private String config;

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


}
