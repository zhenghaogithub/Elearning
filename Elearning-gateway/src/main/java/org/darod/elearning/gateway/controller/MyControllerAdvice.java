package org.darod.elearning.gateway.controller;

import org.apache.shiro.authz.UnauthorizedException;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.response.CommonResponse;
import org.darod.elearning.common.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/30 0030 21:16
 */

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResponse handlerValidationExcpetion(HttpServletRequest request, Exception exception) {
        Map<String, Object> map = new HashMap<>();
        BindException ex = (BindException) exception;
        map.put("errCode", EmException.BINDING_VALIDATION_ERROR.getErrCode());
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        String errorMsg = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
        map.put("errMsg", errorMsg);
        return ResponseUtils.getErrorResponse(map);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResponse handlerException(HttpServletRequest request, Exception exception) {
        Map<String, Object> map = new HashMap<>();
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            map.put("errCode", businessException.getErrCode());
            map.put("errMsg", businessException.getErrMsg());
        } else if (exception instanceof UnauthorizedException) {
            map.put("errCode", EmException.PERMISSION_DENIED.getErrCode());
            map.put("errMsg", EmException.PERMISSION_DENIED.getErrMsg());
//            exception.printStackTrace();
        } else {
            map.put("errCode", EmException.UNKNOWN_ERROR.getErrCode());
            map.put("errMsg", EmException.UNKNOWN_ERROR.getErrMsg());
            exception.printStackTrace();
        }
        return ResponseUtils.getErrorResponse(map);
    }
}
