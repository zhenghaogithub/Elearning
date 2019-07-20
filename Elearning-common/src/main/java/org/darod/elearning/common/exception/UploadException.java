package org.darod.elearning.common.exception;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/19 0019 23:00
 */
//抛出这个异常时需要返回特定的状态码让nginx删除上传的文件
public class UploadException extends BusinessException {
    public UploadException(CommonError commonError) {
        super(commonError);
    }
    public UploadException(CommonError commonError, String errMsg) {
        super(commonError,errMsg);
    }
}
