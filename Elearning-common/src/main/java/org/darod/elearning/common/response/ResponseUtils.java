package org.darod.elearning.common.response;

import org.darod.elearning.common.exception.CommonError;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/30 0030 21:44
 */
public class ResponseUtils {
    public static final int STATUS_OK = 200;
    public static final int STATUS_FAILURE = 400;
    public static final int STATUS_UNKNOWN = 500;
    public static final String MESSAGE_OK = "成功";
    public static final String MESSAGE_FAILURE = "失败";
    public static final String MESSAGE_UNKNOWN = "未知错误";

    public static CommonResponse getOKResponse() {
        return new CommonResponse(STATUS_OK, MESSAGE_OK, null);
    }

    public static CommonResponse getOKResponse(Object data) {
        return new CommonResponse(STATUS_OK, MESSAGE_OK, data);
    }

    public static CommonResponse getErrorResponse(Object erroInfo) {
        return new CommonResponse(STATUS_FAILURE, MESSAGE_FAILURE, erroInfo);
    }
}
