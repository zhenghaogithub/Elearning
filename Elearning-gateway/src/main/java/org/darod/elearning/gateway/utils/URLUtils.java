package org.darod.elearning.gateway.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/24 0024 18:22
 */
public class URLUtils {
    public static String getRealUrlFileName(String name, String suffix) {
        if (StringUtils.isEmpty(name)) return null;
        int i = name.lastIndexOf("/");
        if (i > 0) {
            String fileName = name.substring(i + 1);
            int i2 = fileName.lastIndexOf(suffix);
            if (i2 < 0)
                return fileName;
             else
                return fileName.substring(0, i2);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getRealUrlFileName("/roor/adsafa/qweqwe/zczfd.vsd", "zffadgafadsd"));
    }
}
