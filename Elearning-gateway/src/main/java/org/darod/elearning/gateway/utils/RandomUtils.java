package org.darod.elearning.gateway.utils;

import java.util.Date;
import java.util.Random;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/22 0022 13:15
 */
public class RandomUtils {
    public static final String SEED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int SEED_LENGTH = SEED.length();
    public static final int CHANNEL_PREFIX_LEN = 6;
    public static final int CHANNEL_SUFFIX_LEN = 10;
    public static final int LIVE_SECRET_LEN = 32;

    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(SEED.charAt(random.nextInt(SEED_LENGTH)));
        }
        return sb.toString();
    }
    public static String getRandomChannelId(){
        StringBuilder sb = new StringBuilder();
        String time = String.valueOf(new Date().getTime());
        sb.append(time.substring(time.length()-CHANNEL_PREFIX_LEN));
        sb.append(getRandomString(CHANNEL_SUFFIX_LEN));
        return sb.toString();
    }
    public static String getRandomLiveSecret(){
        return getRandomString(LIVE_SECRET_LEN);
    }

    public static void main(String[] args) {
        System.out.println(getRandomChannelId());
        System.out.println(getRandomLiveSecret());
    }

}
