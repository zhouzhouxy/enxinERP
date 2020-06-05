package com.asura.enxin.entity.dto;

import lombok.Data;

import com.asura.enxin.entity.DFile;
import java.io.Serializable;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/6/3/003 17:10
 */
@Data
public class ApplyDto extends DFile implements Serializable {

    private Integer amount;
}
