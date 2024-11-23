package cn.edu.nbu.fan.converter;

import cn.edu.nbu.fan.response.ReaderResponse;
import cn.edu.nbu.fan.response.Result;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-18:13
 * @since 1.0
 */
public interface ResultConverter<T> {

    Result<T> convert(ReaderResponse response);

}
