package com.mongcent.core.commons.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * @Description AES加、解密工具
 * @Author zl
 * @Date 2019/1/14
 **/
public class AESUtils {
    /**
     * 密钥
     */
    private static final String KEY = "hello.mongcent12";

    private static AES aes = SecureUtil.aes(KEY.getBytes());

    /**
     * 加密
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        if (content == null) {
            return null;
        }
        //加密为16进制表示
        return aes.encryptHex(content);
    }

    /**
     * 解密
     *
     * @param encryptHex 加密的16进制字符串
     * @return
     */
    public static String decrypt(String encryptHex) {
        if (encryptHex == null) {
            return null;
        }
        //解密为字符串
        return aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }
}
