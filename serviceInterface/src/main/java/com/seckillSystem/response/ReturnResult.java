package com.seckillSystem.response;

public class ReturnResult {
    private String status;
    private Object data;

    public static ReturnResult creat(Object object) {
        ReturnResult result = new ReturnResult();
        result.setData(object);
        result.setStatus("success");
        return result;
    }

    public static ReturnResult creat(Object object,String status) {
        ReturnResult result = new ReturnResult();
        result.setData(object);
        result.setStatus(status);
        return result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
