package com.asura.enxin.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/3/003 21:58
 */
@Data
public class ApplyConditionDto implements Serializable {
    private String applyId;
    private String keyword;
    private String state;
    private LocalDateTime date1;
    private LocalDateTime date2;
    private Integer pageNum;
    private Integer pageSize;
}
