package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.SCell;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/5/005 22:28
 */
@Data
public class SCellDto implements Serializable {
    private SCell sCell;
    private String firstKindId;
    private String secondKindId;
    private String thirdKindId;
    private String productId;
    private Integer fileId;
    private String checkTag;
    private String state;
    private Date date1;
    private Date date2;
    private Integer pageNum;
    private Integer pageSize;
}
