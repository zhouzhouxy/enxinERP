package com.asura.enxin.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/6/006 16:50
 */
@Data
public class SGatherConditionDto implements Serializable {
    private String sGatherId;
    private String keyWord;
    private String checkTag;
    private LocalDateTime date1;
    private LocalDateTime date2;
    private Integer pageNum;
    private Integer pageSize;
}
