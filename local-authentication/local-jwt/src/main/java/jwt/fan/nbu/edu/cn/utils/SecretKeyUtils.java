package jwt.fan.nbu.edu.cn.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import java.security.Key;
import java.util.Base64;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-20:57
 * @since 1.0
 */
@UtilityClass
public class SecretKeyUtils {


    public static void main(String[] args) {
        System.out.println(getSecretKey(SignatureAlgorithm.HS256));
    }

    public static String getSecretKey(SignatureAlgorithm alg){
        switch (alg) {
            case HS256:
                Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
                return Base64.getEncoder().encodeToString(key.getEncoded());
            default:
                throw new IllegalArgumentException("Unsupported algorithm");
        }
    }


}
