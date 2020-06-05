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
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/m_manufacture`(`ID`)
 * </p>
 *
 * @author asura
 * @since 2020-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MProceduring implements Serializable {

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

    @TableField("COST_PRICE")
    private BigDecimal costPrice;

    @TableField("SUBTOTAL")
    private BigDecimal subtotal;

    @TableField("PROCEDURE_DESCRIBE")
    private String procedureDescribe;

    @TableField("REG_COUNT")
    private BigDecimal regCount;

    @TableField("PROCEDURE_RESPONSIBLE_PERSON")
    private String procedureResponsiblePerson;

    @TableField("REGISTER")
    private String register;

    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    @TableField("CHECKER")
    private String checker;

    @TableField("CHECK_TIME")
    private LocalDateTime checkTime;


}
