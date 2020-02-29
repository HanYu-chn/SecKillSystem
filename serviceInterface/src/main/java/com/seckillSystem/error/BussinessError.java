package com.seckillSystem.error;

public interface BussinessError {
    public int getCode();
    public String getErrMsg();
    public void setErrMsg(String errMsg);
}
