package cn.edu.nbu.fan.constants;

import cn.edu.nbu.fan.converter.DefaultConverter;
import cn.edu.nbu.fan.converter.DefaultNumConverter;
import cn.edu.nbu.fan.converter.DeviceTimeFromDeviceConverter;
import cn.edu.nbu.fan.response.ReaderResponse;
import cn.edu.nbu.fan.response.Result;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-14:29
 * @since 1.0
 */
@Getter
public enum Command {

    // 指令 0x01 设备状态查询
    DEVICE_STATUS_FROM_SERVICE("01",2,v -> DefaultConverter.instance.convert(v),false,"查询设备状态命令"),
    DEVICE_STATUS_FROM_DEVICE("01",1,v -> DefaultConverter.instance.convert(v),true,"返回设备状态查询结果"),

    // 指令 0x02 获取设备 ID
    DEVICE_ID_FROM_SERVICE("02",2,v -> DefaultConverter.instance.convert(v),false,"查询设备id命令"),
    DEVICE_ID_FROM_DEVICE("02",1,v -> DefaultNumConverter.instance.convert(v),true,"返回设备id查询结果"),


    // 指令 0x03 更新或查询扫码器系统时间
    DEVICE_TIME_FROM_SERVICE("03",2,v -> DefaultConverter.instance.convert(v),true,"查询或更新扫码器系统时间命令"),
    DEVICE_TIME_FROM_DEVICE("03",1,v -> DeviceTimeFromDeviceConverter.instance.convert(v),true,"返回扫码器系统时间查询结果"),

    // 指令 0x04 蜂鸣器和 LED 控制
    DEVICE_BEEPER_AND_LED_FROM_SERVICE("04",2,v -> DefaultConverter.instance.convert(v),true,"蜂鸣器和 LED 控制命令"),
    DEVICE_BEEPER_AND_LED_FROM_DEVICE("04",1,v -> DefaultConverter.instance.convert(v),true,"返回蜂鸣器和 LED 控制结果"),

    // 指令 0x05 开关扫码功能
    DEVICE_SWITCH_FROM_SERVICE("05",2,v -> DefaultConverter.instance.convert(v),true,"开关扫码功能命令"),
    DEVICE_SWITCH_FROM_DEVICE("05",1,v -> DefaultConverter.instance.convert(v),true,"返回开关扫码功能结果"),


    // 指令 0x09 开关门磁上报
    DEVICE_DOOR_FROM_SERVICE("09",2,v -> DefaultConverter.instance.convert(v),true,"开关门磁上报命令"),
    DEVICE_DOOR_FROM_DEVICE("09",1,v -> DefaultConverter.instance.convert(v),true,"返回开关门磁上报结果"),

    // 指令 0x0A 获取设备 SN
    DEVICE_SN_FROM_SERVICE("0A",2,v -> DefaultConverter.instance.convert(v),false,"获取设备 SN 命令"),
    DEVICE_SN_FROM_DEVICE("0A",1,v -> DefaultConverter.instance.convert(v),true,"返回获取设备 SN 结果"),

    // 指令 0x21 QR、条码、NFC 设置
    DEVICE_QR_FROM_SERVICE("21",2,v -> DefaultConverter.instance.convert(v),true,"QR、条码、NFC 设置命令"),
    DEVICE_QR_FROM_DEVICE("21",1,v -> DefaultConverter.instance.convert(v),true,"返回 QR、条码、NFC 设置结果"),

    // 指令 0x22 扫码工作模式设置
    DEVICE_WORKMODE_FROM_SERVICE("22",2,v -> DefaultConverter.instance.convert(v),true,"扫码工作模式设置命令"),
    DEVICE_WORKMODE_FROM_DEVICE("22",1,v -> DefaultConverter.instance.convert(v),true,"返回扫码工作模式设置结果"),

    // 指令 0x23 间隔模式下扫码时间间隔设置
    DEVICE_INTERVAL_FROM_SERVICE("23",2,v -> DefaultConverter.instance.convert(v),true,"间隔模式下扫码时间间隔设置命令"),
    DEVICE_INTERVAL_FROM_DEVICE("23",1,v -> DefaultConverter.instance.convert(v),true,"返回间隔模式下扫码时间间隔设置结果"),

    // 指令 0x24 LED 背光控制
    DEVICE_LED_FROM_SERVICE("24",2,v -> DefaultConverter.instance.convert(v),true,"LED 背光控制命令"),
    DEVICE_LED_FROM_DEVICE("24",1,v -> DefaultConverter.instance.convert(v),true,"返回 LED 背光控制结果"),

    // 指令 0x25 蜂鸣器响应配置
    DEVICE_BEEPER_FROM_SERVICE("25",2,v -> DefaultConverter.instance.convert(v),true,"蜂鸣器响应配置命令"),
    DEVICE_BEEPER_FROM_DEVICE("25",1,v -> DefaultConverter.instance.convert(v),true,"返回蜂鸣器响应配置结果"),

    // 指令 0x26 GPIO_0 控制
    DEVICE_GPIO0_FROM_SERVICE("26",2,v -> DefaultConverter.instance.convert(v),true,"GPIO_0 控制命令"),
    DEVICE_GPIO0_FROM_DEVICE("26",1,v -> DefaultConverter.instance.convert(v),true,"返回 GPIO_0 控制结果"),

    // 指令 0x27 GPIO_1 控制
    DEVICE_GPIO1_FROM_SERVICE("27",2,v -> DefaultConverter.instance.convert(v),true,"GPIO_1 控制命令"),
    DEVICE_GPIO1_FROM_DEVICE("27",1,v -> DefaultConverter.instance.convert(v),true,"返回 GPIO_1 控制结果"),

    // 指令 0x28 GPIO_0 和 GPIO_1 输出高电平电压控制
    DEVICE_GPIO01_FROM_SERVICE("28",2,v -> DefaultConverter.instance.convert(v),true,"GPIO_0 和 GPIO_1 输出高电平电压控制命令"),
    DEVICE_GPIO01_FROM_DEVICE("28",1,v -> DefaultConverter.instance.convert(v),true,"返回 GPIO_0 和 GPIO_1 输出高电平电压控制结果"),

    // 指令 0x29 音频控制
    DEVICE_AUDIO_FROM_SERVICE("29",2,v -> DefaultConverter.instance.convert(v),true,"音频控制命令"),
    DEVICE_AUDIO_FROM_DEVICE("29",1,v -> DefaultConverter.instance.convert(v),true,"返回音频控制结果"),

    // 指令 0x2A 继电器控制   数据： 0x01: 开 ,0x00: 关
    DEVICE_RELAY_FROM_SERVICE("2A",2,v -> DefaultConverter.instance.convert(v),true,"继电器控制命令"),
    DEVICE_RELAY_FROM_DEVICE("2A",1,v -> DefaultConverter.instance.convert(v),true,"返回继电器控制结果"),

    // 指令 0x2B 心跳使能与心跳上报
    // 注意 DEVICE_ENABLE_HEARTBEAT_FROM_SERVICE数据部分应为 json字符串.toBytes("UTF-8")，无需额外编码
    DEVICE_ENABLE_HEARTBEAT_FROM_SERVICE("2B",2,v -> DefaultConverter.instance.convert(v),true,"心跳使能与心跳上报命令"),
    DEVICE_ENABLE_HEARTBEAT_FROM_DEVICE("2B",2,v -> DefaultConverter.instance.convert(v),true,"心跳功能开启结果"),
    DEVICE_HEARTBEAT_FROM_SERVICE("2B",1,v -> DefaultConverter.instance.convert(v),true,"返回心跳使能与心跳上报结果"),
    DEVICE_HEARTBEAT_FROM_DEVICE("2B",1,v -> DefaultConverter.instance.convert(v),true,"返回心跳使能与心跳上报结果"),

    // 指令 0x37 查询版本号指令
    DEVICE_VERSION_FROM_SERVICE("37",2,v -> DefaultConverter.instance.convert(v),false,"查询版本号命令"),
    DEVICE_VERSION_FROM_DEVICE("37",1,v -> DefaultConverter.instance.convert(v),true,"返回查询版本号结果"),

    // 指令 0x30 获取结果不区分数据来源
    DEVICE_RESULT_FROM_SERVICE("30",2,v -> DefaultConverter.instance.convert(v),false,"获取结果不区分数据来源命令"),
    DEVICE_RESULT_FROM_DEVICE("30",1,v -> DefaultConverter.instance.convert(v),true,"返回获取结果不区分数据来源结果"),

    // 指令 0x33 获取结果区分数据来源
    DEVICE_RESULT_FROM_SERVICE2("33",2,v -> DefaultConverter.instance.convert(v),false,"获取结果区分数据来源命令"),
    DEVICE_RESULT_FROM_DEVICE2("33",1,v -> DefaultConverter.instance.convert(v),true,"返回获取结果区分数据来源结果"),

    // 指令 0x32 按键值上报
    DEVICE_BUTTON_FROM_SERVICE2("32",2,v -> DefaultConverter.instance.convert(v),false,"按键值上报命令"),
    DEVICE_BUTTON_FROM_DEVICE2("32",1,v -> DefaultConverter.instance.convert(v),false,"返回按键值上报结果")

    ;


    /**
     * 命令字
     */
    private String word;

    /**
     * 1 device-> service, 2  service -> device
     */
    private Integer direction;

    /**
     * 是否有数据
     */
    private Boolean hasData;

    /**
     * 结果处理函数
     */
    private Function<ReaderResponse, Result> converter;

    /*
     * 指令说明
     */
    private String desc;

    Command(String word, Integer direction, Function<ReaderResponse, Result> converter,Boolean hasData, String desc) {
        this.word = word;
        this.direction = direction;
        this.converter = converter;
        this.hasData = hasData;
        this.desc = desc;
    }


    public static Command getCommand(String commandWord){
        for (Command command : Command.values()) {
            if(command.word.equals(commandWord)){
                return command;
            }
        }
        return null;
    }



}
