package com.api.utils;

import java.io.Serializable;

public class BackEndResp implements Serializable {
    private static final long serialVersionUID = 1L;

    public final  String SUCCESS = "000000";
    public final  String FAIL = "111111";
    private String respCode = FAIL ;

    private String respMsg = "";

    private Object data;

    public static BackEndResp build(){
        return new BackEndResp();
    }
    public static BackEndResp build(String respCode){
        return new BackEndResp(respCode);
    }
    private BackEndResp(){}
    private BackEndResp(String respCode){
        this.respCode = respCode;
        if (this.respCode.equals(FAIL)){
            this.setRespMsg("后台出错！");
        }else {
            this.setRespMsg("操作成功！");
        }
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        if (respCode.equals(this.FAIL)){
            this.setRespMsg("操作失败");
        }else {
            this.setRespMsg("操作成功");
        }
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
