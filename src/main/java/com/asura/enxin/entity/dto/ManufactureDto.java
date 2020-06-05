package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.MDesignProcedureDetails;
import com.asura.enxin.entity.MManufacture;
import com.asura.enxin.entity.MProcedure;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/4/004 16:46
 */
@Data
public class ManufactureDto implements Serializable {
    private MManufacture mManufacture;

//    private MDesignProcedure designProcedure;
//    private List<MDesignProcedureDetails> list;
    private List<MProcedure> list;
}
