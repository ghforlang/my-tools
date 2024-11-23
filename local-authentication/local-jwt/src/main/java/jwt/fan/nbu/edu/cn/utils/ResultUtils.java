package jwt.fan.nbu.edu.cn.utils;

import com.alibaba.fastjson.JSON;
import jwt.fan.nbu.edu.cn.model.ResponseModel;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-18:06
 * @since 1.0
 */
@UtilityClass
public class ResultUtils {

    public static <T> void success(HttpServletResponse response,ResponseModel<T> data) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fail(HttpServletResponse response, ResponseModel<?> model){
        response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(model));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fail(HttpServletResponse response,Integer httpStatus,ResponseModel<?> model){
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(model));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
