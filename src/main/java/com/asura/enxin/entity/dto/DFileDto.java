package com.asura.enxin.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/27/027 19:25
 */
@Data
public class DFileDto implements Serializable {
    private String firstKindId;
    private String secondKindId;
    private String thirdKindId;
    private String type;
    private String dma;  //物料组成标志
    private String dpt;  //工序组成标志
    private String deleteTag;   //删除标志
    private String dct;         //库存分配标志
    private Date date1;
    private Date date2;
    private Integer pageNum;
    private Integer pageSize;
}
