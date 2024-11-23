import cn.edu.nbu.fan.PackageUtils;
import cn.edu.nbu.fan.constants.Command;
import cn.edu.nbu.fan.response.ReaderResponse;
import cn.edu.nbu.fan.response.Result;
import com.alibaba.fastjson.JSON;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-18:56
 * @since 1.0
 */
public class Test {
    public static void main(String[] args) {
//        testDataParse("55AA030008005D7A121F74010000AB",Command.DEVICE_TIME_FROM_DEVICE);
//        testDataParse("55AA020004008000000079",Command.DEVICE_ID_FROM_DEVICE);
    }

    private static void testDataParse(String responseData,Command command){
        ReaderResponse response = PackageUtils.parseReaderResponse(responseData, command);
        Result result = command.getConverter().apply(response);
        System.out.println(JSON.toJSONString(result));
    }
}
