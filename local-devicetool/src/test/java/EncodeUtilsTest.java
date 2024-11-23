import cn.edu.nbu.fan.DataEncodeUtils;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-14:01
 * @since 1.0
 */
public class EncodeUtilsTest {


    public static void main(String[] args) {
        String encodeData = DataEncodeUtils.encodeStrDataOnly("hello world");
        System.out.println("after encoding : " + encodeData);
        String decodeData = DataEncodeUtils.decodeStrDataOnly(encodeData);
        System.out.println("after decoding : " + decodeData);

//        System.out.println(ByteUtils.bytesToHex(PackageHeaders.));
    }
}
