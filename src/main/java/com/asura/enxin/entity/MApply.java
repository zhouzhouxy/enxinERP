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
public class MApply implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("APPLY_ID")
    private String applyId;

    @TableField("PRODUCT_ID")
    private String productId;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("PRODUCT_DESCRIBE")
    private String productDescribe;

    @TableField("TYPE")
    private String type;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("DESIGNER")
    private String designer;

    @TableField("REMARK")
    private String remark;

    @TableField("REGISTER")
    private String register;

    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    @TableField("CHECKER")
    private String checker;

    @TableField("CHECK_SUGGESTION")
    private String checkSuggestion;

    @TableField("CHECK_TIME")
    private LocalDateTime checkTime;

    @TableField("CHECK_TAG")
    private String checkTag;

    @TableField("MANUFACTURE_TAG")
    private String manufactureTag;


}
