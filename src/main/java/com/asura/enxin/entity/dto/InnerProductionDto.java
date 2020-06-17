package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MProcedure;
import com.asura.enxin.entity.MProceduring;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/10/010 15:46
 */
@Data
public class InnerProductionDto implements Serializable {
    private MProcedure mProcedure;
    private MProceduring mProceduring;
    private List<InnerProcedureModuleDto> mProcedureModule;
//    private List<InnerProcedureModule2Dto> mProcedureModule2;
}
