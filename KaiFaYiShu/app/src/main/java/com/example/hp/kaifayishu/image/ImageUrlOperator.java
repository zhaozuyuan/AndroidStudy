package com.example.hp.kaifayishu.image;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author:zuyuan
 * @date：2018/11/24
 * @note: 获取图片网址的MD5值 以此作为key
 */
public class ImageUrlOperator {
    public static String hashKeyFromUrl(String url) {
        String cacheKey = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }

        return cacheKey;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }

        return sb.toString();
    }
}
