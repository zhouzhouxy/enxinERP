package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MDesignProcedure;
import com.asura.enxin.entity.MDesignProcedureDetails;
import com.asura.enxin.entity.MProcedure;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/1/001 10:31
 */
@Data
public class ProcedureDesignDto implements Serializable {


    private MDesignProcedure designProcedure;
    private List<MDesignProcedureDetails> list;
}
