package com.silent.framework.wechat.utils.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silent.framework.wechat.utils.WechatStringUtils;


/**
 * 请求校验工具类 
 * @author TanJin
 * @date 2015-7-21
 */
public class SignUtil {
	protected static Logger logger = LoggerFactory.getLogger(SignUtil.class);
    
    /**
     * 验证签名
     * @date 2015-7-21
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {  
        String[] arr = new String[] {WechatConstant.token, timestamp, nonce};  
        logger.info("[SignUtil] [token:{}] [timestamp:{}] [nonce:{}]", WechatConstant.token, timestamp, nonce);
        
        if(WechatStringUtils.isEmpty(signature) || WechatStringUtils.isEmpty(timestamp) || WechatStringUtils.isEmpty(nonce)) {
        	return false;
        }
        
        // 将token、timestamp、nonce三个参数进行字典序排序  
        Arrays.sort(arr);  
        StringBuilder content = new StringBuilder();  
        for (int i = 0; i < arr.length; i++) {  
            content.append(arr[i]);  
        }  
        String tmpStr = sha1(content.toString());  	//进行sha1数字签名
        logger.info("[SignUtil] [sha1 after tmpStr:{}]", tmpStr);
        
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信  
        return tmpStr != null ? tmpStr.equals(signature.toLowerCase()) : false;  
    }  
    
    /**
     * 对给定字符串内容进行sha1数字签名
     * @date 2015-8-28
     */
    public static String sha1(String content) {
    	 MessageDigest md = null;  
    	 String tmpStr = null;  
    	 try {  
             md = MessageDigest.getInstance("SHA-1");  
             // 将三个参数字符串拼接成一个字符串进行sha1加密  
             byte[] digest = md.digest(content.getBytes());  
             tmpStr = byteToStr(digest);  
         } catch (NoSuchAlgorithmException e) {  
             e.printStackTrace();  
         }
    	
    	return tmpStr.toLowerCase();
    } 
    
    /** 
     * 将字节数组转换为十六进制字符串 
     * @date 2015-7-21
     */  
    private static String byteToStr(byte[] byteArray) {  
        String strDigest = "";  
        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  
        return strDigest;  
    }
    
    /** 
     * 将字节转换为十六进制字符串 
     * @date 2015-7-21
     */  
    private static String byteToHexStr(byte mByte) {  
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
  
        String s = new String(tempArr);  
        return s;  
    }
}
