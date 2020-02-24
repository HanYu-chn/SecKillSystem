package com.hanyu.project.error;

public enum CommonError implements BussinessError{
    PARAMETER_VALIDATION_ERROR(00001,"参数不合法"),
    USER_NOT_EXISTS_ERROR(10001,"用户不存在"),
    ITEM_NOT_EXISTS_ERROR(30001,"商品不存在"),
    UNKOWN_ERROR(00000,"未知错误"),
    STOCK_NOT_ENOUGH(20000,"库存不足"),
    STOCK_INFO_ERROR(20001,"库存信息错误")
    ;
    private int code;
    private String errMsg;

    CommonError(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
