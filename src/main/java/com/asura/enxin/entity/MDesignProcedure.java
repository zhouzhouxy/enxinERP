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
public class MDesignProcedure implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;


    @TableField("DESIGN_ID")
    private String designId;

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

    @TableField("PRODUCT_ID")
    private String productId;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("PROCEDURE_DESCRIBE")
    private String procedureDescribe;

    @TableField("COST_PRICE_SUM")
    private BigDecimal costPriceSum;

    @TableField("MODULE_COST_PRICE_SUM")
    private BigDecimal moduleCostPriceSum;

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

    @TableField("CHECK_SUGGESTION")
    private String checkSuggestion;

    @TableField("CHECK_TAG")
    private String checkTag;

    @TableField("CHANGER")
    private String changer;

    @TableField("CHANGE_TIME")
    private LocalDateTime changeTime;

    @TableField("CHANGE_TAG")
    private String changeTag;

    @TableField("DESIGN_MODULE_TAG")
    private String designModuleTag;

    @TableField("DESIGN_MODULE_CHANGE_TAG")
    private String designModuleChangeTag;


}
