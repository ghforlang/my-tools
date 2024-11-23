package cn.edu.nbu.fan;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-14:26
 * @since 1.0
 */
public class ByteUtils {
    /**
     * @param data
     * @return
     */
    public static byte[] intToByteArray(Integer data){
        return intToByteArray(data,1);
    }

    /**
     *
     * @param data
     * @param order 1小端 2大端
     * @return
     */
    public static byte[] intToByteArray(Integer data, Integer order){
        ByteBuffer bufferLittleEndian = ByteBuffer.allocate(4);
        if(Objects.equals(1,order)){
            bufferLittleEndian.order(ByteOrder.LITTLE_ENDIAN);
            bufferLittleEndian.putInt(data);
        }else{
            bufferLittleEndian.order(ByteOrder.BIG_ENDIAN);
            bufferLittleEndian.putInt(data);
        }
        return bufferLittleEndian.array();
    }




    /**
     *
     * @param data
     * @return
     */
    public static byte[] hexStrToByteArray(String data){
        int len = data.length();
        byte[] byteData = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            byteData[i / 2] = (byte) ((Character.digit(data.charAt(i), 16) << 4)
                    + Character.digit(data.charAt(i + 1), 16));
        }
        return byteData;
    }

    public static String byteToHexStr(byte  byteData){
        return String.format("%02X", byteData & 0xFF);
    }

    /**
     * 字节数组转为16进制字符
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = "0123456789ABCDEF".toCharArray();
        char[] hexArray = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexArray[i * 2] = hexChars[v >>> 4];
            hexArray[i * 2 + 1] = hexChars[v & 0x0F];
        }
        return new String(hexArray);
    }


    public static byte[] hexStrToBytes(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            throw new IllegalArgumentException("输入的16进制字符串不合法");
        }
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            char c1 = hex.charAt(i);
            char c2 = hex.charAt(i + 1);
            int nibble1 = Character.digit(c1, 16);
            int nibble2 = Character.digit(c2, 16);
            if (nibble1 == -1 || nibble2 == -1) {
                throw new IllegalArgumentException("字符串包含非16进制字符");
            }
            bytes[i / 2] = (byte) ((nibble1 << 4) | nibble2);
        }
        return bytes;
    }

    public static byte checkXorBytes(byte[] bytes){
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("Input byte array must not be null or empty");
        }

        byte result = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            result ^= bytes[i];
        }

        return result;
    }

    public static String hexStringEndianConvert(String hexStr) {
        // 将小端序十六进制字符串转换为字符数组
        char[] chars = hexStr.toCharArray();
        int length = chars.length;

        // 创建一个 StringBuilder 来构建大端序十六进制字符串
        StringBuilder bigEndianHex = new StringBuilder(length);
        for(int i = chars.length-1; i >= 0; i -=2){
            bigEndianHex.append(chars[i-1]);
            bigEndianHex.append(chars[i]);
        }

        return bigEndianHex.toString();
    }
}
