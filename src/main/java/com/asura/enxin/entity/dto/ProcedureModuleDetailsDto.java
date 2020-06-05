package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.MDesignProcedureDetails;
import com.asura.enxin.entity.MDesignProcedureModule;
import lombok.Data;

import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/2/002 15:22
 */
@Data
public class ProcedureModuleDetailsDto {

    private MDesignProcedureDetails details;
    private List<MDesignProcedureModule> list;

}
