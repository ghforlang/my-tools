package cn.edu.nbu.fan.converter;

import cn.edu.nbu.fan.response.ReaderResponse;
import cn.edu.nbu.fan.response.Result;
import lombok.NoArgsConstructor;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-19:01
 * @since 1.0
 */
@NoArgsConstructor
public class DefaultNumConverter implements ResultConverter<Long> {

    public static final DefaultNumConverter instance = DefaultNumConverter.Holder.INSTANCE;
    static class Holder { static final DefaultNumConverter INSTANCE = new DefaultNumConverter();
    }


    @Override
    public Result<Long> convert(ReaderResponse response) {
        if ("00".equals(response.getCode())) {
            return Result.success(Long.valueOf(response.getResult(),16));
        }
        return Result.fail(response.getResult());
    }
}
