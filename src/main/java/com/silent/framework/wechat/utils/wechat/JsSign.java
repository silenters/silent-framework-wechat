package com.silent.framework.wechat.utils.wechat;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.silent.framework.wechat.service.JsapiTicketService;

/**
 * 微信 JS-SDK 前端页面需要信息
 * @author TanJin 
 * @date 2017-1-20
 */
public class JsSign {
	private static final Logger logger = LoggerFactory.getLogger(JsSign.class);

	/**
	 * 生成 JS-SDK需要的权限信息<br>
	 * 
	 * 返回项：<br>
	 * url<br>
	 * jsapi_ticket<br>
	 * nonceStr<br>
	 * timestamp<br>
	 * signature<br>
	 * @param 当前访问页面全路径URL
	 * @date 2017-1-20
	 */
	public static JSONObject sign(String url) {
        String jsapi_ticket = JsapiTicketService.getInstance().queryJsapiTicket();
        JSONObject result = new JSONObject();
        
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        logger.debug("[JsSign] [sign] [str:{}]", string1);
        
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
        	logger.error("", e);
        } catch (UnsupportedEncodingException e) {
        	logger.error("", e);
        }

        result.put("url", url);
        result.put("jsapi_ticket", jsapi_ticket);
        result.put("nonceStr", nonce_str);
        result.put("timestamp", timestamp);
        result.put("signature", signature);
        logger.info("[wechat] [JsSign] [sign] [result:{}]", result.toString());
        return result;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
