package com.asura.enxin.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * InnoDB free: 6144 kB; (`PARENT_ID`) REFER `enxin/s_pay`(`ID`)
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SPayDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("PARENT_ID")
    private Integer parentId;

    @TableField("PRODUCT_ID")
    private String productId;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("PRODUCT_DESCRIBE")
    private String productDescribe;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("AMOUNT_UNIT")
    private String amountUnit;

    @TableField("COST_PRICE")
    private BigDecimal costPrice;

    @TableField("SUBTOTAL")
    private BigDecimal subtotal;

    @TableField("PAID_AMOUNT")
    private BigDecimal paidAmount;

    @TableField("PAY_TAG")
    private String payTag;


}
