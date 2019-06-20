package org.darod.elearning.common.exception;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:06
 */
public interface CommonError {
    int getErrCode();
    String getErrMsg();
    CommonError setErrMsg(String errMsg);
}
