package pers.tmw.booksharing.util;

import org.springframework.util.DigestUtils;

/**
 * Created by tmw090906 on 2017/6/7.
 * MD5加密工具类
 */
public class MD5Util {

    public static String getMD5(String origin){

        String base = origin + PropertiesUtil.getProperty("mail.salt");
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
