package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MProcedureModule;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/10/010 15:45
 */
@Data
public class InnerProcedureModule2Dto extends MProcedureModule implements Serializable {
    private Integer thisAmount;
}
