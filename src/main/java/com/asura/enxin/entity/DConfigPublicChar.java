package com.asura.enxin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/26/026 16:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DConfigPublicChar {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String kind;

    private String typeId;

    private String typeName;

    private String describe1;

    private String describe2;
}
