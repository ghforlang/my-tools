package cn.edu.nbu.fan.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-18:14
 * @since 1.0
 */
@Getter
@Setter
public class Result<T> {
    /**
     * 状态码 0 -成功，其他失败
     */
    private int code;

    /**
     * 结果message
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result(0, "success",data);
    }

    public static Result fail(String message){
        return new Result(-1,message);
    }



}
