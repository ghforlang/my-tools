package cn.edu.nbu.fan;


import java.nio.charset.StandardCharsets;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-13:49
 * @since 1.0
 */
public class DataEncodeUtils {

    /**
     * 对数据报文进行编码
     * @param data
     * @return
     */
    public static String encodeStrDataOnly(String data){
        data = ByteUtils.hexStringEndianConvert(data);
        byte[] dataBytes = ByteUtils.hexStrToByteArray(data);
        return ByteUtils.bytesToHex(dataBytes);
    }

    public static String decodeStrDataOnly(String hexStr){
        return new String(ByteUtils.hexStrToBytes(hexStr), StandardCharsets.UTF_8);
    }



}
