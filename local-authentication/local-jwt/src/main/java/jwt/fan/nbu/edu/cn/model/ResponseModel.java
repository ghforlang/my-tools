package jwt.fan.nbu.edu.cn.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-20:07
 * @since 1.0
 */
@Getter
@Setter
public class ResponseModel<T> implements Serializable {

    /**
     * 0- 成功，1-失败
     */
    private Integer code;

    private String message = "操作失败";
    private T data;
    private Boolean success = false;

    public static <T> ResponseModel success(T data){
        ResponseModel response = new ResponseModel();
        response.setCode(0);
        response.setData(data);
        response.setMessage("操作成功");
        response.setSuccess(true);
        return response;
    }

    public static <T> ResponseModel fail(String message){
        ResponseModel response = new ResponseModel();
        response.setCode(1);
        response.setMessage(message);
        return response;
    }
}
