package org.darod.elearning.gateway.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/8 0008 21:23
 */
public class ValidateUtils {
    private static final String phoneNumRegex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    public static boolean isPhoneNumLegal(String phone) {
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(phoneNumRegex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public static String getRamdomOtp() {
        //生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        return String.valueOf(randomInt);
    }

    public static void main(String[] args) {
        System.out.println(isPhoneNumLegal("13131313131"));
    }
}
