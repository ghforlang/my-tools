package cn.edu.nbu.fan.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-15:18
 * @since 1.0
 */

@Getter
@Setter
public class ReaderResponse {

    /**
     * 结果长度，=0 result为空
     */
    private int resultLen = 0;

    /**
     * 成功- 0 ，其他为失败
     */
    private String code;

    /**
     * 结果,内容为读卡器返回的hex字符串
     */
    private String result;

    public ReaderResponse(String code, String result){
        this.code = code;
        this.result = result;
    }

    public ReaderResponse(){
        this("0", "success");
    }

    public static ReaderResponse ofSuccess(){
        return new ReaderResponse();
    }

    public static ReaderResponse response(String code,String result){
        return new ReaderResponse(code,result);
    }


}
