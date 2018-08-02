package com.rimi.vo;

/**
 * 用于前后端数据交互的对象
 */
public class ResponseResult {
    //成功
    public static Result success(Object object){
        return new Result(1,"成功",object);
    }
    //失败
    public static Result error(int code,String msg,Object object){
        return new Result(code,msg,object);
    }
}
