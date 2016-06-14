package com.ivan.wei.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: ivan
 * Date: 16/6/14
 * Time: 下午6:48
 * Version: 1.0
 */
@Controller
@RequestMapping("/weixin")
public class HomeController {
    @RequestMapping("/signature")
    @ResponseBody
    public String  checkSignature(String signature,String timestamp
            ,String nonce,String echostr) throws NoSuchAlgorithmException {
        String token = "17217acf5cg4a4i6h8";
        String tmpStr = getSHA1(token, timestamp, nonce);

        System.out.println("+++++++++++++++++++++tmpStr   " + tmpStr);
        System.out.println("---------------------signature   " + signature);

        if (tmpStr.equals(signature)) {
            return echostr;
        } else {
            return null;
        }
    }

        /**
         * 用SHA1算法生成安全签名
         * @param token 票据
         * @param timestamp 时间戳
         * @param nonce 随机字符串
         * @return 安全签名
         * @throws NoSuchAlgorithmException
         */
    public  String getSHA1(String token, String timestamp, String nonce) throws NoSuchAlgorithmException {
        String[] array = new String[] { token, timestamp, nonce };
        StringBuffer sb = new StringBuffer();
        // 字符串排序
        Arrays.sort(array);
        for (int i = 0; i < 3; i++) {
            sb.append(array[i]);
        }
        String str = sb.toString();
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
