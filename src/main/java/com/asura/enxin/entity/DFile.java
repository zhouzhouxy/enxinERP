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
@Accessors(chain = true)
public class DFile implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("PRODUCT_ID")
    private String productId;

    @TableField("PRODUCT_NAME")
    private String productName;

    @TableField("FACTORY_NAME")
    private String factoryName;

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

    @TableField("PRODUCT_NICK")
    private String productNick;

    @TableField("TYPE")
    private String type;

    @TableField("PRODUCT_CLASS")
    private String productClass;

    @TableField("PERSONAL_UNIT")
    private String personalUnit;

    @TableField("PERSONAL_VALUE")
    private String personalValue;

    @TableField("PROVIDER_GROUP")
    private String providerGroup;

    @TableField("WARRANTY")
    private String warranty;

    @TableField("TWIN_NAME")
    private String twinName;

    @TableField("TWIN_ID")
    private String twinId;

    @TableField("LIFECYCLE")
    private String lifecycle;

    @TableField("LIST_PRICE")
    private BigDecimal listPrice;

    @TableField("COST_PRICE")
    private BigDecimal costPrice;

    @TableField("REAL_COST_PRICE")
    private BigDecimal realCostPrice;

    @TableField("AMOUNT_UNIT")
    private String amountUnit;

    @TableField("PRODUCT_DESCRIBE")
    private String productDescribe;

    @TableField("RESPONSIBLE_PERSON")
    private String responsiblePerson;

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

    @TableField("CHANGER")
    private String changer;

    @TableField("CHANGE_TIME")
    private LocalDateTime changeTime;

    @TableField("CHANGE_TAG")
    private String changeTag;

    @TableField("PRICE_CHANGE_TAG")
    private String priceChangeTag;

    @TableField("FILE_CHANGE_AMOUNT")
    private Integer fileChangeAmount;

    @TableField("DELETE_TAG")
    private String deleteTag;

    @Override
    public String toString() {
        return "DFile{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", factoryName='" + factoryName + '\'' +
                ", firstKindId='" + firstKindId + '\'' +
                ", firstKindName='" + firstKindName + '\'' +
                ", secondKindId='" + secondKindId + '\'' +
                ", secondKindName='" + secondKindName + '\'' +
                ", thirdKindId='" + thirdKindId + '\'' +
                ", thirdKindName='" + thirdKindName + '\'' +
                ", productNick='" + productNick + '\'' +
                ", type='" + type + '\'' +
                ", productClass='" + productClass + '\'' +
                ", personalUnit='" + personalUnit + '\'' +
                ", personalValue='" + personalValue + '\'' +
                ", providerGroup='" + providerGroup + '\'' +
                ", warranty='" + warranty + '\'' +
                ", twinName='" + twinName + '\'' +
                ", twinId='" + twinId + '\'' +
                ", lifecycle='" + lifecycle + '\'' +
                ", listPrice=" + listPrice +
                ", costPrice=" + costPrice +
                ", realCostPrice=" + realCostPrice +
                ", amountUnit='" + amountUnit + '\'' +
                ", productDescribe='" + productDescribe + '\'' +
                ", responsiblePerson='" + responsiblePerson + '\'' +
                ", register='" + register + '\'' +
                ", registerTime=" + registerTime +
                ", checker='" + checker + '\'' +
                ", checkTime=" + checkTime +
                ", checkTag='" + checkTag + '\'' +
                ", changer='" + changer + '\'' +
                ", changeTime=" + changeTime +
                ", changeTag='" + changeTag + '\'' +
                ", priceChangeTag='" + priceChangeTag + '\'' +
                ", fileChangeAmount=" + fileChangeAmount +
                ", deleteTag='" + deleteTag + '\'' +
                ", designModuleTag='" + designModuleTag + '\'' +
                ", designProcedureTag='" + designProcedureTag + '\'' +
                ", designCellTag='" + designCellTag + '\'' +
                '}';
    }

    @TableField("DESIGN_MODULE_TAG")
    private String designModuleTag;

    @TableField("DESIGN_PROCEDURE_TAG")
    private String designProcedureTag;

    @TableField("DESIGN_CELL_TAG")
    private String designCellTag;


}
