package com.asura.enxin.utils;

/**
 * @author asura
 * @version 1.0.0
 * @date 2020/5/25/025 19:38
 */
public class BaseResponseInfo {
    public int code;
    public Object data;

    public BaseResponseInfo() {
        code = 400;
        data = null;
    }
}
