package cn.edu.nbu.fan.converter;

import cn.edu.nbu.fan.response.ReaderResponse;
import cn.edu.nbu.fan.response.Result;
import lombok.NoArgsConstructor;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-18:28
 * @since 1.0
 */
@NoArgsConstructor
public class DefaultConverter implements ResultConverter<String>{

    public static final DefaultConverter instance = Holder.INSTANCE;


    static class Holder { static final DefaultConverter INSTANCE = new DefaultConverter();
    }



    @Override
    public Result<String> convert(ReaderResponse response) {
        if ("00".equals(response.getCode())) {
            return Result.success(response.getResult());
        }
        return Result.fail(response.getResult());
    }


}
