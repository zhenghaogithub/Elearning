package org.darod.elearning.common.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 通用返回类型
 *
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:04
 */

public class CommonResponse {
    @JSONField(ordinal = 1)
    private Integer status;
    @JSONField(ordinal = 2)
    private String message;
    @JSONField(ordinal = 3)
    private Object data;

    public CommonResponse(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
