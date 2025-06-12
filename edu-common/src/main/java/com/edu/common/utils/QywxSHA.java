package com.edu.common.utils;

import java.security.MessageDigest;

public class QywxSHA {

    /**
     * 用SHA1算法生成安全签名
     * @param jsapi_ticket 票据
     * @param nonce 随机字符串
     * @param timestamp 时间戳
     * @param url 网址
     * @return 安全签名
     */
    public static String getSHA1(String jsapi_ticket, String nonce, String timestamp,  String url) throws Exception
    {

        String templateStr = "jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s";
        String str = String.format(templateStr,jsapi_ticket,nonce,timestamp,url);
        // SHA1签名生成
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();

    }
}
