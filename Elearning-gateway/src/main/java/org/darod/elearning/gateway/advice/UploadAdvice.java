package org.darod.elearning.gateway.advice;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.darod.elearning.common.exception.BusinessException;
import org.darod.elearning.common.exception.EmException;
import org.darod.elearning.common.exception.UploadException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/19 0019 23:09
 */
@Aspect
@Order(1)
@Component
public class UploadAdvice {
    @Pointcut("execution(public  * org.darod.elearning.gateway.controller.UploadImageController.*(..))")
    void uploadMethod() {}
    @AfterThrowing(pointcut = "uploadMethod()", throwing = "ex")
    public void doUploadMethodException(Throwable ex) {
        if(ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;
            throw new UploadException(businessException);
        }else
            throw new UploadException(EmException.UNKNOWN_ERROR,"文件上传失败");
    }
}
