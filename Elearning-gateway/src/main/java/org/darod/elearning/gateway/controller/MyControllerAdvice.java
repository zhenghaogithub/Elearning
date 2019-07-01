package org.darod.elearning.gateway.controller;

import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/30 0030 21:16
 */

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResponse handlerException(HttpServletRequest request, Exception exception) {
        Map<String, Object> map = new HashMap<>();
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            map.put("errCode", businessException.getErrCode());
            map.put("errMsg", businessException.getErrMsg());
        } else {
            map.put("errCode", EmException.UNKNOWN_ERROR.getErrCode());
            map.put("errMsg", EmException.UNKNOWN_ERROR.getErrMsg());
            exception.printStackTrace();
        }
        return ResponseUtils.getErrorResponse(map);
    }
}
