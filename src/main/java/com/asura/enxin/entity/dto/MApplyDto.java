package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MApply;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/3/003 17:13
 */
@Data
public class MApplyDto implements Serializable{
    private List<ApplyDto> dto;
    private MApply mApply;
}
