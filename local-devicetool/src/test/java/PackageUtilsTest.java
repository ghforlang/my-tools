import cn.edu.nbu.fan.ByteUtils;
import cn.edu.nbu.fan.PackageUtils;
import cn.edu.nbu.fan.constants.Command;
import cn.edu.nbu.fan.response.ReaderResponse;

import java.nio.charset.StandardCharsets;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-17:34
 * @since 1.0
 */
public class PackageUtilsTest {

    public static void main(String[] args) {

        testGeneratePcRequest();
//        testParseReaderResponse();

    }

    private static void testGeneratePcRequest(){
        String request = "hello world";
        System.out.println(PackageUtils.generatePcRequest(request,Command.DEVICE_STATUS_FROM_SERVICE));
    }

    private static void testParseReaderResponse(){
        String responseData = "55AA030008005D7A121F74010000AB";
        ReaderResponse response = PackageUtils.parseReaderResponse(responseData, Command.DEVICE_TIME_FROM_DEVICE);
        System.out.println(response.getCode());
        System.out.println(response.getResult());
        System.out.println(response.getResultLen());
    }
}
