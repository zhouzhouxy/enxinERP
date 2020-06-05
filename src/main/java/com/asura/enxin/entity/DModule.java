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
public class DModule implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("DESIGN_ID")
    private String designId;

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

    @TableField("DESIGNER")
    private String designer;

    @TableField("MODULE_DESCRIBE")
    private String moduleDescribe;

    @TableField("COST_PRICE_SUM")
    private BigDecimal costPriceSum;

    @TableField("REGISTER")
    private String register;

    @TableField("REGISTER_TIME")
    private LocalDateTime registerTime;

    @TableField("CHECKER")
    private String checker;

    @TableField("CHECK_TIME")
    private LocalDateTime checkTime;

    @TableField("CHANGER")
    private String changer;

    @TableField("CHANGE_TIME")
    private LocalDateTime changeTime;

    @TableField("CHECK_TAG")
    private String checkTag;

    @TableField("CHANGE_TAG")
    private String changeTag;


    public DModule() {
    }

    public DModule(Integer id, String designId, String productId, String productName,
                   String firstKindId, String firstKindName, String secondKindId, String secondKindName, String thirdKindId, String thirdKindName, String designer, String moduleDescribe, BigDecimal costPriceSum, String register, LocalDateTime registerTime, String checker, LocalDateTime checkTime, String changer, LocalDateTime changeTime, String checkTag, String changeTag) {
        this.id = id;
        this.designId = designId;
        this.productId = productId;
        this.productName = productName;
        this.firstKindId = firstKindId;
        this.firstKindName = firstKindName;
        this.secondKindId = secondKindId;
        this.secondKindName = secondKindName;
        this.thirdKindId = thirdKindId;
        this.thirdKindName = thirdKindName;
        this.designer = designer;
        this.moduleDescribe = moduleDescribe;
        this.costPriceSum = costPriceSum;
        this.register = register;
        this.registerTime = registerTime;
        this.checker = checker;
        this.checkTime = checkTime;
        this.changer = changer;
        this.changeTime = changeTime;
        this.checkTag = checkTag;
        this.changeTag = changeTag;
    }
}
