package com.asura.enxin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_design_procedure`(`ID`)
 * </p>
 *
 * @author jobob
 * @since 2020-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MDesignProcedureDetails implements Serializable {

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

    @TableField("PROCEDURE_DESCRIBE")
    private String procedureDescribe;

    @TableField("AMOUNT_UNIT")
    private String amountUnit;

    @TableField("COST_PRICE")
    private BigDecimal costPrice;

    @TableField("SUBTOTAL")
    private BigDecimal subtotal;

    @TableField("MODULE_SUBTOTAL")
    private BigDecimal moduleSubtotal;

    @TableField("REGISTER")
    private String register;

    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    @TableField("DESIGN_MODULE_TAG")
    private String designModuleTag;

    @TableField("DESIGN_MODULE_CHANGE_TAG")
    private String designModuleChangeTag;



}
