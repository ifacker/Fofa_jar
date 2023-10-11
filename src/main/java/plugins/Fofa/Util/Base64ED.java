package plugins.Fofa.Util;

import java.util.Base64;

public class Base64ED {

    /**
     * 对字符串进行编码
     * @param code 输入要编码的字符串
     * @return 输出编码后的字符串
     */
    public static String encode(String code){
        return Base64.getEncoder().encodeToString(code.getBytes());
    }

    /**
     * 对字符串进行解码
     * @param code 输入要解码的字符串
     * @return 输出解码后的字符串
     */
    public static String decode(String code){
        return new String(Base64.getDecoder().decode(code));
    }
}
