package com.mybatisplus.utils;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseMsg {
    /**
     * 响应码
     */
    private int status;
    /**
     * 是否成功 true/false
     */
    private boolean state;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * Object 返回参数
     */
    private Object data;
    /**
     * 返回条数
     */
    private Integer count;


    public ResponseMsg() {
    }

    /**
     * 不含count条数
     *
     * @param status
     * @param msg
     * @param data
     */
    public ResponseMsg(int status, boolean state, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.state = state;
    }

    /**
     * 全参对象
     *
     * @param status
     * @param msg
     * @param data
     * @param count
     */
    public ResponseMsg(int status, boolean state, String msg, Object data, Integer count) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.count = count;
        this.state = state;
    }

    public static ResponseMsg newErrorMsg(String errorMsg) {
        if (Strings.isNullOrEmpty(errorMsg)) {
            return new ResponseMsg(HttpStatus.OK.value(), false, "操作失败", "");
        } else {
            return new ResponseMsg(HttpStatus.OK.value(), false, errorMsg, "");
        }
    }

    public static ResponseMsg newOkMsg(String okMsg) {
        if (Strings.isNullOrEmpty(okMsg)) {
            return new ResponseMsg(HttpStatus.OK.value(), true, "操作成功", "");
        } else {
            return new ResponseMsg(HttpStatus.OK.value(), true, okMsg, "");
        }
    }

    public static ResponseMsg newDataMsg(Object returnData) {
        if (returnData != null) {
            return new ResponseMsg(HttpStatus.OK.value(), true, "", returnData);
        } else {
            return new ResponseMsg(HttpStatus.OK.value(), true, "", "");
        }
    }

}