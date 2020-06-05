package com.asura.enxin.entity.vo;

import lombok.Data;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/26/026 9:24
 */
@Data
public class Result {
    private String message;
    private Boolean success;


    public Result(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public Result() {
    }
}
