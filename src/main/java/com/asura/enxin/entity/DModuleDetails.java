package com.asura.enxin.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/d_module`(`ID`)
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DModuleDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("PARENT_ID")
    private Integer parentId;

    @TableField("DETAILS_NUMBER")
    private BigDecimal detailsNumber;

    @TableField("PRODUCT_ID")
    private String productId;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("TYPE")
    private String type;

    @TableField("PRODUCT_DESCRIBE")
    private String productDescribe;

    @TableField("AMOUNT_UNIT")
    private String amountUnit;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("RESIDUAL_AMOUNT")
    private BigDecimal residualAmount;

    @TableField("COST_PRICE")
    private BigDecimal costPrice;

    @TableField("SUBTOTAL")
    private BigDecimal subtotal;


}
