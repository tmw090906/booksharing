package pers.tmw.booksharing.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by tmw090906 on 2017/6/7.
 * 返回信息高复用封装类
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化时不序列NULL对象
public class ServerResponse<T> implements Serializable{
    private int status;
    private String msg;
    private T date;

    private ServerResponse(int status){
        this.status = status;
    }

    private ServerResponse(int status,T date){
        this.status = status;
        this.date = date;
    }

    private ServerResponse(int status,String msg,T date){
        this.status = status;
        this.msg = msg;
        this.date = date;
    }

    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getDate() {
        return date;
    }

    //成功消息体?为什么这么几个，以下同问题
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> ServerResponse<T> createBySuccess(T date){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),date);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T date){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,date);
    }
    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    //失败消息体
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    //自定义错误消息体
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }
}
