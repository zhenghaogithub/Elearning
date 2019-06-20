package org.darod.elearning.common.exception;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:09
 */
public enum  EnumBusinessException implements CommonError {

    PARAMETER_VALIDATION_ERROR(10000,"参数不合法"),
    UNKNOWN_ERROR(10001,"未知错误"),

    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码错误"),
    USER_NOT_LOGIN(20003,"用户还未登录"),


    ;
    private int errCode;
    private String msg;
    EnumBusinessException(int errCode ,String msg){
        this.errCode = errCode;
        this.msg = msg;
    }

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return msg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.msg = errMsg;
        return this;
    }
}
