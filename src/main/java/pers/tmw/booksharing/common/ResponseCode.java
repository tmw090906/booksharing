package pers.tmw.booksharing.common;

/**
 * Created by tmw090906 on 2017/6/7.
 * 返回信息体状态枚举类
 */
public enum  ResponseCode {

    ERROR(0,"ERROR"),
    SUCCESS(1,"SUCCESS"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return this.code;
    }
    public String getDesc(){
        return this.desc;
    }

}
