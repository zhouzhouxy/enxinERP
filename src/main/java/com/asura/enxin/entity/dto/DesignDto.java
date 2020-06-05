package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.DModule;
import com.asura.enxin.entity.DModuleDetails;
import lombok.Data;

import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/29/029 16:17
 */
@Data
public class DesignDto{

    private DModule dModule;
    private List<DModuleDetails> details;
}
