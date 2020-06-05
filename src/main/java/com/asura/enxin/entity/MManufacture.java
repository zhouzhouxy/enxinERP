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
 * @author asura
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MManufacture implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("MANUFACTURE_ID")
    private String manufactureId;

    @TableField("PRODUCT_ID")
    private String productId;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("TESTED_AMOUNT")
    private BigDecimal testedAmount;

    @TableField("APPLY_ID_GROUP")
    private String applyIdGroup;

    @TableField("PRODUCT_DESCRIBE")
    private String productDescribe;

    @TableField("MODULE_COST_PRICE_SUM")
    private BigDecimal moduleCostPriceSum;

    @TableField("REAL_MODULE_COST_PRICE_SUM")
    private BigDecimal realModuleCostPriceSum;

    @TableField("LABOUR_COST_PRICE_SUM")
    private BigDecimal labourCostPriceSum;

    @TableField("REAL_LABOUR_COST_PRICE_SUM")
    private BigDecimal realLabourCostPriceSum;

    @TableField("DESIGNER")
    private String designer;

    @TableField("REGISTER")
    private String register;

    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    @TableField("CHECKER")
    private String checker;

    @TableField("CHECK_TIME")
    private LocalDateTime checkTime;

    @TableField("REMARK")
    private String remark;

    @TableField("CHECK_TAG")
    private String checkTag;

    @TableField("MANUFACTURE_PROCEDURE_TAG")
    private String manufactureProcedureTag;


}
