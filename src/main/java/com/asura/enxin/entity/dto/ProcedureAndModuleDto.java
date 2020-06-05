package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MManufacture;
import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.MProcedureModule;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/4/004 23:23
 */
@Data
public class ProcedureAndModuleDto implements Serializable {

    private MProcedure mProcedure;
    private List<MProcedureModule> list;
}
