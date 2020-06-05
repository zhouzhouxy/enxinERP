package com.asura.enxin.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/27/027 10:02
 */
@Data
public class PageResult<T> {
    private Long total;
    private List<T> list;
    private Boolean aBoolean;

    public PageResult(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PageResult() {
    }
}
