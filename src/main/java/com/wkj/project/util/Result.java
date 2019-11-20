package com.wkj.project.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    int code;
    String msg;
    String serial;
    long cost;
    Object data;

    public static Result getResult(){
        return getResult(ErrorCode.OP_ERROR);
    }

    public static Result getResult(Object data){
        if(data == null){
            return getResult(ErrorCode.OP_NODATA);
        }else{
            return getResult(ErrorCode.OP_SUCCESS,data);
        }
    }
    public static Result getResult(ErrorCode code, Object data,Object ...args){
        String msg = code.getMsg();
        Result result = new Result();
        if(!StrUtil.isEmpty(msg)){
            result.setCode(code.getCode());
            result.setMsg(String.format(code.getMsg(),args));
        }
        if (data != null){
            result.setData(data);
        }
        return result;
    }
    public static Result result(Integer code, String message,Object data){
        return new Result(code,message,null,0,data);
    }
    public static Result getResult(ErrorCode code){
        return getResult(code ,null);
    }
    public static Result error(ErrorCode code){
        return error(code.getCode(),code.getMsg());
    }
    public static Result error(ErrorCode code,Object data){
        return error(code.getCode(),code.getMsg(),data);
    }
    public static Result error(Integer code, String message) {
        return result(code,message,null);
    }
    public static Result error(Integer code, String message,Object data) {
        return result(code,message,data);
    }
    public static Result success() {
        return getResult(ErrorCode.OP_SUCCESS);
    }
    public static Result success(Object obj){
        return getResult(ErrorCode.OP_SUCCESS,obj);
    }
}
