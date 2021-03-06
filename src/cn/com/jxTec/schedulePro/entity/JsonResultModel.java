package cn.com.jxTec.schedulePro.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回对象
 * @author Administrator
 *
 * @param <T>
 */
public class JsonResultModel<T> implements Serializable {

    public static final String SUCCESS_CODE = "999999";

    private static final long serialVersionUID = 4150639926975975243L;

    protected String code;
    protected String msg;
    protected T data;
    protected boolean success;

    public JsonResultModel(){}

    public JsonResultModel(boolean success){
        if (success) {
            this.code = SUCCESS_CODE;
        }
        this.success = success;
    }

    public JsonResultModel(T data){
        this.data = data;
        this.success = true;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", this.isSuccess());
        result.put("code", this.getCode());
        if (this.isSuccess()) {
            result.put("data", this.getData());
        } else {
            result.put("msg", this.getMsg());
        }
        return result;
    }
}