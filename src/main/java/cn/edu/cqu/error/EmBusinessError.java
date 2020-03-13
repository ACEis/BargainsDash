package cn.edu.cqu.error;

public enum EmBusinessError implements CommonError {

    //通用错误类型10000
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),

    UNKNOWN_ERROR(10002, "未知错误"),

    //10000开头为用户信息相关错误定义,
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FAIL(20002, "用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003,"用户未登录"),

    //交易错误
    STOCK_NOT_ENOUGH(30001, "库存不足"),
    MQ_SENT_FAIL(30002,"异步库存消息发送失败"),
    RATELIMIT(30003,"系统繁忙，请稍后再试")
    ;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
