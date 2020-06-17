package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MProcedureModuling;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/12/012 10:52
 */
@Data
public class MProcedureModulingDto implements Serializable {
    private MProcedureModuling mProcedureModuling;
    private String register;
    private LocalDateTime registerTime;
}
