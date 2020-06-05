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
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_manufacture`(`ID`)
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MProcedure implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("PARENT_ID")
    private Integer parentId;

    @TableField("DETAILS_NUMBER")
    private BigDecimal detailsNumber;

    @TableField("PROCEDURE_ID")
    private String procedureId;

    @TableField("PROCEDURE_NAME")
    private String procedureName;

    @TableField("LABOUR_HOUR_AMOUNT")
    private BigDecimal labourHourAmount;

    @TableField("REAL_LABOUR_HOUR_AMOUNT")
    private BigDecimal realLabourHourAmount;

    @TableField("SUBTOTAL")
    private BigDecimal subtotal;

    @TableField("REAL_SUBTOTAL")
    private BigDecimal realSubtotal;

    @TableField("MODULE_SUBTOTAL")
    private BigDecimal moduleSubtotal;

    @TableField("REAL_MODULE_SUBTOTAL")
    private BigDecimal realModuleSubtotal;

    @TableField("COST_PRICE")
    private BigDecimal costPrice;

    @TableField("DEMAND_AMOUNT")
    private BigDecimal demandAmount;

    @TableField("REAL_AMOUNT")
    private BigDecimal realAmount;

    @TableField("PROCEDURE_FINISH_TAG")
    private String procedureFinishTag;

    @TableField("PROCEDURE_TRANSFER_TAG")
    private String procedureTransferTag;


}
