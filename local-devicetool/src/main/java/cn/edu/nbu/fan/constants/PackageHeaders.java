package cn.edu.nbu.fan.constants;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-14:00
 * @since 1.0
 */
public class PackageHeaders {

    /**
     * 命令头+ 命令字 + 标识字 + 长度字+ 数据域+ 校验字
     *
     * 命令头：两字节，默认为 0x55，0xAA
     * 命令字：一字节
     * 标识字：一字节， 0x00 则代表成功应答，其它失败或错误,读卡器结果字段，请求无此字段
     * 长度字：两字节，指明本条命令从长度字后面开始到校验字的字节数（不含效验字），低位在前
     * 数据域：此项可以为空
     * 校验字：一字节，从命令头到数据域最后一字节的逐字节异或值
     */

    /**
     * 通用头 2字节
     */
    public static final int HEAD_BYTE_LEN = 2;

    /**
     * 命令字 1字节
     */
    public static final int CMD_BYTE_LEN = 1;

    /**
     * 标识字 1字节 (reader返回特有)
     */
    public static final int ID_BYTE_LEN = 1;

    /**
     * 数据包长度， 2字节
     */
    public static final int LENGTH_BYTE_LEN = 2;

    /**
     * 校验字长度 1字节
     */
    public static final int CHECKSUM_BYTE_LEN = 1;

    /**
     * 通用字
     */
    public static final String COMMON_HEAD_STR = "55AA";

    /**
     * 通用报文长度
     */
    public static final int COMMON_LEN = HEAD_BYTE_LEN + CMD_BYTE_LEN + LENGTH_BYTE_LEN + CHECKSUM_BYTE_LEN;
}
