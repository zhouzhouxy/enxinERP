package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MProcedure;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/11/011 22:54
 */
@Data
public class MProcedureDto implements Serializable {
    private MProcedure mProcedure;
    private Integer nextId;
}
