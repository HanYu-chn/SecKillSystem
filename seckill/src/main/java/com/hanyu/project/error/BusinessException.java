package com.hanyu.project.error;

public class BusinessException extends RuntimeException implements BussinessError {
    private BussinessError bussinessError;

    public BusinessException(BussinessError bussinessError) {
        this.bussinessError = bussinessError;
    }

    public BusinessException(BussinessError bussinessError,String errMsg) {
        this.bussinessError = bussinessError;
        this.bussinessError.setErrMsg(errMsg);
    }

    public BussinessError getBussinessError() {
        return bussinessError;
    }

    @Override
    public int getCode() {
        return bussinessError.getCode();
    }

    @Override
    public String getErrMsg() {
        return bussinessError.getErrMsg();
    }

    @Override
    public void setErrMsg(String errMsg) {
        bussinessError.setErrMsg(errMsg);
    }
}
