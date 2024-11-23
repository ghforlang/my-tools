package cn.edu.nbu.fan.converter;

import cn.edu.nbu.fan.response.ReaderResponse;
import cn.edu.nbu.fan.response.Result;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-18:17
 * @since 1.0
 */
public class DeviceTimeFromDeviceConverter implements ResultConverter<LocalDateTime>{

    public static final DeviceTimeFromDeviceConverter instance = DeviceTimeFromDeviceConverter.Holder.INSTANCE;
    static class Holder { static final DeviceTimeFromDeviceConverter INSTANCE = new DeviceTimeFromDeviceConverter();}



    @Override
    public Result<LocalDateTime> convert(ReaderResponse response) {
        if ("00".equals(response.getCode())) {
            long timeStamp = Long.valueOf(response.getResult(),16);
            Instant instant = Instant.ofEpochMilli(timeStamp);
            // 将 Instant 转换为 LocalDateTime，指定时区
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            return Result.success(localDateTime);
        }
        return Result.fail(response.getResult());
    }
}
