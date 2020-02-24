package com.hanyu.project.error;

public interface BussinessError {
    public int getCode();
    public String getErrMsg();
    public void setErrMsg(String errMsg);
}
