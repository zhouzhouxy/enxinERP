package com.asura.enxin.entity.dto;

import com.asura.enxin.entity.MDesignProcedureModule;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/2/002 11:52
 */
@Data
public class ProcedureDetailDto implements Serializable {
    private Integer id;
    private String productId;    //
    private List<MDesignProcedureModule> list;
}
