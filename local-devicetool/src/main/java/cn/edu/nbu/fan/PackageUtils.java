package cn.edu.nbu.fan;

import cn.edu.nbu.fan.constants.Command;
import cn.edu.nbu.fan.constants.PackageHeaders;
import cn.edu.nbu.fan.response.ReaderResponse;
import org.apache.commons.lang3.StringUtils;


/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-14:12
 * @since 1.0
 */
public class PackageUtils {


    public static  String generatePcRequest(String data,Command command){
        if(command.getDirection() != 2){
            System.out.println(command.getDesc() + "非[PC-> Reader] 指令,请检查,命令字:" + command.getWord());
            throw new IllegalArgumentException(command.getDesc() + "非[PC-> Reader] 指令,请检查,命令字:" + command.getWord());
        }

        int dataLen = 0;
        byte[] dataBytes = null;
        if(command.getHasData() && StringUtils.isNotBlank(data)){
            dataBytes = ByteUtils.hexStrToByteArray(data);
            dataLen = dataBytes.length;
        }

        int capacity = PackageHeaders.COMMON_LEN + dataLen;
        int destIndex = 0;
        byte[] tmpPackBytes = new byte[capacity - 1];
        byte[] packBytes = new byte[capacity];
        System.arraycopy(ByteUtils.hexStrToByteArray(PackageHeaders.COMMON_HEAD_STR),0,tmpPackBytes,destIndex,PackageHeaders.HEAD_BYTE_LEN);
        destIndex += PackageHeaders.HEAD_BYTE_LEN;
        System.arraycopy(ByteUtils.hexStrToByteArray(command.getWord()),0,tmpPackBytes,destIndex,PackageHeaders.CMD_BYTE_LEN);
        if(dataLen > 0){
            destIndex += PackageHeaders.CMD_BYTE_LEN;
            System.arraycopy(ByteUtils.intToByteArray(dataLen),0,tmpPackBytes,destIndex,PackageHeaders.LENGTH_BYTE_LEN);
            destIndex += PackageHeaders.LENGTH_BYTE_LEN;
            System.arraycopy(dataBytes,0,tmpPackBytes,destIndex,dataLen);
        }

        // 校验和
        byte checkResult = ByteUtils.checkXorBytes(tmpPackBytes);
        System.arraycopy(tmpPackBytes,0,packBytes,0,tmpPackBytes.length);
        System.arraycopy(new byte[]{checkResult},0,packBytes,tmpPackBytes.length,1);
        return ByteUtils.bytesToHex(packBytes);
    }

    public static ReaderResponse parseReaderResponse(String data,Command command){
        if(command.getDirection() != 1){
            System.out.println(command.getDesc() + "非[Reader -> PC] 指令,请检查,命令字:" + command.getWord());
            throw new IllegalArgumentException(command.getDesc() + "非[Reader -> PC] 指令,请检查,命令字:" + command.getWord());
        }

        String dataWithOutCheckSum = data.substring(0,data.length() - 2);
        byte checkResult = ByteUtils.checkXorBytes(ByteUtils.hexStrToBytes(dataWithOutCheckSum));
        String checkSum = data.substring(data.length() - 2);
        if(!checkSum.equals(ByteUtils.byteToHexStr(checkResult))){
            System.out.println(command.getDesc() + "报文校验码异常，请确认,命令字:" + command.getWord());
            throw new IllegalArgumentException(command.getDesc() + "报文校验码异常，请确认,命令字:" + command.getWord());
        }


        // 截取header
        int startIndex = 0;
        int endIndex = PackageHeaders.HEAD_BYTE_LEN * 2;
        String packHeader = data.substring(startIndex,endIndex);
        if(!PackageHeaders.COMMON_HEAD_STR.equals(packHeader)){
            System.out.println(command.getDesc() + "无效header,请检查,packHeader:" + packHeader);
            throw new IllegalArgumentException(command.getDesc() + "无效packHeader,请检查,packHeader:" + packHeader);
        }

        // 截取命令字
        startIndex = endIndex;
        endIndex += PackageHeaders.CMD_BYTE_LEN * 2;
        String packCommandWord = data.substring(startIndex, endIndex);
        if(!command.getWord().equals(packCommandWord)){
            System.out.println(command.getDesc() + "命令字异常,请检查,命令字:" + command.getWord());
            throw new IllegalArgumentException(command.getDesc() + "无效header,请检查,命令字:" + command.getWord());
        }

        // 截取响应标识符
        startIndex = endIndex;
        endIndex += PackageHeaders.ID_BYTE_LEN * 2;
        String packIdentifyValue = data.substring(startIndex,endIndex);

        // 截取响应数据长度
        startIndex = endIndex;
        endIndex += PackageHeaders.LENGTH_BYTE_LEN * 2;
        String packDataLengthStr = data.substring(startIndex,endIndex);
        int packDataLength = Integer.parseInt(ByteUtils.hexStringEndianConvert(packDataLengthStr),16);

        // 截取数据内容
        if(packDataLength > 0){
            startIndex = endIndex;
            endIndex += packDataLength * 2;
            String packData = data.substring(startIndex,endIndex);
            packData = ByteUtils.hexStringEndianConvert(packData);
            ReaderResponse response = ReaderResponse.response("00",packData);
            response.setResultLen(packDataLength);
            return response;

        }else{
            if(packIdentifyValue.equals("00")){
                return ReaderResponse.ofSuccess();
            }else{
                return ReaderResponse.response("01","请求失败!");
            }
        }
    }


}
