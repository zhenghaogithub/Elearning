package org.darod.elearning.common.exception;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/6/20 0020 9:09
 */
public enum EmException implements CommonError {

    PARAMETER_VALIDATION_ERROR(10000, "参数不合法"),
    UNKNOWN_ERROR(10001, "未知错误"),

    BINDING_VALIDATION_ERROR(10002, "参数绑定失败，请检查参数组合是否合理"),

    PERMISSION_DENIED(30000, "您没有权限"),

    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户名或密码错误"),
    USER_NOT_LOGIN(20003, "用户还未登录"),

    COURSE_NOT_EXIST(40000, "课程不存在"),

    ORDER_NOT_EXIST(40001, "订单不存在"),

    COURSE_NOT_PURCHASED(40002, "课程未购买"),

    COURSE_HAVE_LEARNED(40003, "您已经学习该课程"),

    COURSE_NOT_LEARNED(40004, "您还未学习该课程"),

    COURSE_HAVE_PURCHASED(40005, "您已经购买过该课程，请勿重复购买"),

    ORDER_DELETED(40006, "订单已删除"),

    ORDER_EXPIRED(40007, "订单已过期,请重新下单"),

    CHAPTER_NOT_EXIST(40100, "章节不存在"),

    COMMENT_NOT_EXIST(40101, "评论不存在"),

    CHAPTER_VIDEO_NOT_EXIST(40102, "章节暂无视频"),

    TEACHER_HAVE_APPLY(40200, "您已经申请过了"),

    TEACHER_NOT_EXIST(40201, "教师不存在"),

    LIVE_HAVE_APPLY(50000, "您已开启过直播"),

    LIVE_ROOM_NOT_EXIST(50001, "直播间不存在"),

    LIVE_HAVE_STOP(50002, "直播已经停止"),

    ;
    private int errCode;
    private String msg;

    EmException(int errCode, String msg) {
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
