package com.asura.enxin.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/28/028 19:10
 */
@Data
public class MaterialDto implements Serializable {
     private String firstKindId;
    private String secondKindId;
    private String thirdKindId;
    private Integer goodsId;
    private String state;
    private Integer pageNum;
    private Integer pageSize;
    private Date date1;
    private Date date2;
}
