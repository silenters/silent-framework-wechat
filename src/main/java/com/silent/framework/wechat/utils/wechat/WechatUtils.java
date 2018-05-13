package com.silent.framework.wechat.utils.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.silent.framework.wechat.utils.WechatStringUtils;
import com.silent.framework.wechat.utils.http.WechatHttpUtils;

/**
 * 微信服务号处理 工具类
 * 包括：
 * 获取access_token、JS ticket、
 * 微信用户信息、用户地理位置信息、创建带参数二维码
 * @author TanJin
 * @date 2015-8-2
 */
public class WechatUtils {
	private static Logger logger = LoggerFactory.getLogger(WechatUtils.class);
	
	/**
	 * 不弹出授权页面，直接跳转，只能获取用户openID
	 */
	public final static String SCOPE_SNSAPI_BASE = "snsapi_base";		
	/**
	 * 弹出授权页面，可通过openID拿到昵称、性别、所在地。并且，
	 * 即使在未关注的情况下，只要用户授权，也能获取其信息
	 */
	public final static String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";
	
	// 获取access_token的接口地址（GET） 限200（次/天）  
	public final static String ACCESS_TOKEN_URL = 
			"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";  
	
	//创建二维码ticket URL
	private final static String TICKET_URL = 
			"https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	
	//获取jsapi_ticket
	private final static String JSAPI_TICKET = 
			"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	//获取微信用户信息
	private final static String WECHAT_USER_INFO_URL = 
			"https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
	
	//推送链接
	private final static String PUSH_TEMPLATE_MESSAGE_URL = 
			"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN"; 
	
	//网页授权获取code URL
	private final static String WEB_AUTHORIZATION_GET_CODE_URL = 
			"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	//网页授权通过code获取openID及access_token
	private final static String WEB_AUTHORIZATION_GET_INFO_URL = 
			"https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	//网页授权拉取用户信息（需要scope为snsapi_userinfo）
	private final static String WEB_AUTHORIZATION_GET_USERINFO_URL = 
			"https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	//长链接转短链接接口
	private final static String WEB_SHORT_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
	
	//客服接口--发消息
	private final static String CUSTOM_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	
	/**
     * 生成带参数的二维码--永久二维码。<br><br>
     * 
     * 永久二维码，是无过期时间的，但数量较少（目前为最多10万个）。<br>
     * 永久二维码主要用于适用于帐号绑定、用户来源统计等场景。<br>
     */
    private static final String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";
    
    /**
     * 生成带参数的二维码--临时二维码。<br>
     */
    private static final String QR_SCENE = "QR_SCENE";
	
	/** 
	 * 获取access_token凭证。<br><br>
	 * 
	 * 返回数据格式：<br>
	 * access_token（获取到的凭证）。<br>
	 * expires_in（凭证有效时间，单位：秒）。<br>
	 * 
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return 
	 */  
	public static JSONObject getAccessToken(String appid, String appsecret) {  
	    String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
	    
	    try {
	    	JSONObject jsonObject = (JSONObject) WechatHttpUtils.doGetToJson(requestUrl, null);
		    logger.debug("[Wechat] [WechatUtils] [accessToken json:{}]", jsonObject);
		    
		    // 如果请求成功  
		    if (null != jsonObject) {  
	            String accessToken = jsonObject.getString("access_token");
	            if(WechatStringUtils.isNotEmpty(accessToken)) {
	            	return jsonObject;
	            } else {
	            	// 获取token失败  
		            logger.error("[Wechat] [WechatUtils] [获取token失败] [errcode:{}] [errmsg:{}]", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg")); 
	            }
		    }  
		} catch (Exception e) {
			logger.error("", e);
		}
	    return null;
	}
	
	 /** 
     * 获取JS ticket<br><br>
     * 
     * 说明：<br>
     * jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。<br>
     * 由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket。<br>
     * 
     * 返回数据格式：。<br>
     * errcode:错误码，0表示成功。<br>
     * errmsg：错误码描述，ok表示成功。<br>
     * ticket：用户调用微信JS接口的临时票据。<br>
     * expires_in：有效时间（单位：秒）。<br>
     * 
     * @return 
     */  
    public static JSONObject getJsTicket (String accessToken) {  
    	 String requestUrl = JSAPI_TICKET.replace("ACCESS_TOKEN", accessToken);  
    	 
    	 try {
			JSONObject jsonObject = (JSONObject) WechatHttpUtils.doGetToJson(requestUrl, null);
			logger.debug("[Wechat] [WechatUtils] [JsTicket json:{}]", jsonObject);
			
			if(null != jsonObject){
				String ticket = jsonObject.getString("ticket");
				if(WechatStringUtils.isNotEmpty(ticket)) {
					return jsonObject;
				} else {
					// 获取Ticket失败  
		            logger.error("[Wechat] [WechatUtils] [获取Ticket失败] [errcode:{}] [errmsg:{}]", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));  
				}
	         }
		} catch (Exception e) {
			logger.error("", e);
		}
        return null;
    } 
    
    /**
     * 获取微信用户信息<br><br>
     * 
     * 返回数据格式：<br>
     * subscribe：用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。<br>
     * openid：用户的标识，对当前公众号唯一。<br>
     * nickname：用户的昵称。<br>
     * sex：用户的性别，值为1时是男性，值为2时是女性，值为0时是未知。<br>
     * city：用户所在城市。<br>
     * country：用户所在国家。<br>
     * province：用户所在省份。<br>
     * language：用户的语言，简体中文为zh_CN。<br>
     * headimgurl：用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。<br>
     * subscribe_time：用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间。<br>
     * unionid：只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。<br>
     * remark：公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注。<br>
     * groupid：用户所在的分组ID。<br>
     * 
     * @date 2015-9-1
     */
    public static JSONObject getWechatUserInfo (String accessToken, String openId) {
    	String requestUrl = WECHAT_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);  
    	try {
			JSONObject jsonObject = (JSONObject) WechatHttpUtils.doGetToJson(requestUrl, null);
			logger.debug("[Wechat] [WechatUtils] [wechat user info json:{}]", jsonObject);
			
			if(null != jsonObject) {
				if(jsonObject.getIntValue("errcode") != WechatCode.success){
					// 获取微信用户信息失败  
		            logger.error("[Wechat] [WechatUtils] [获取微信用户信息失败] [errcode:{}] [errmsg:{}]", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));  
				} else {
					return jsonObject;
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
       return null;
    }
    
    /**
     * 向指定微信用户推送模板消息信息<br><br>
     * 
     * 返回数据格式：<br>
     * errcode:错误码，0表示成功<br>
     * errmsg：错误码描述，ok表示成功<br>
     * msgid：消息ID<br>
     * 
     * @param accessToken 访问凭证
     * @param message 推送的模板消息
     * @date 2015-10-10
     */
    public static boolean pushTemplateMessage (String accessToken, JSONObject message) {
    	String requestUrl = PUSH_TEMPLATE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);  
    	try {
    		JSONObject jsonObject = WechatHttpUtils.doPostToJson(requestUrl, message, null);
    		logger.debug("[Wechat] [WechatUtils] [pushTemplateMessage] [result:{}]", jsonObject.toString());
    		
    		if(null != jsonObject){
    			if(jsonObject.getIntValue("errcode") != WechatCode.success) {
    				logger.error("[Wechat] [WechatUtils] [pushTemplateMessage] [result:{}]", jsonObject.toString());
    			}
        		return jsonObject.getIntValue("errcode") == WechatCode.success;
        	}
		} catch (Exception e) {
			logger.error("", e);
		}
    	return false;
    }
    
	/**
	 * 创建永久带参数二维码ticket<br><br>
	 * 
	 * 返回数据格式：<br>
	 * ticket：获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。<br>
	 * url：二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片。<br>
	 * 
	 * @param accessToken
	 * @param sceneStr 二维码携带的参数
	 * @author TanJin
	 * @date 2015-8-5
	 */
	public static JSONObject createPermanentQrCodeTicket (String accessToken, String sceneStr) {
		//拼接获取二维码ticket的URL链接
		String requestUrl = TICKET_URL.replace("ACCESS_TOKEN", accessToken);
		
		JSONObject scene_str = new JSONObject();
		scene_str.put("scene_str", sceneStr);
		
		JSONObject scene = new JSONObject();
		scene.put("scene", scene_str);
		
		JSONObject data = new JSONObject();
		data.put("action_name", QR_LIMIT_STR_SCENE);
		data.put("action_info", scene);
		
		try {
			JSONObject jsonObject = WechatHttpUtils.doPostToJson(requestUrl, data, null);
			logger.debug("[Wechat] [WechatUtils] [create qr code ticket json:{}]", jsonObject);
			
			if(null != jsonObject){
				if(jsonObject.getIntValue("errcode") != WechatCode.success){
		            logger.error("[Wechat] [WechatUtils] [创建永久二维码ticket失败] [errcode:{}] [errmsg:{}]", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));  
				}
			}else{
				logger.debug("[Wechat] [WechatUtils] [ticket json is empty]");
			}
			return jsonObject;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	/**
	 * 创建临时二维码ticket<br><br>
	 * 
	 * 返回数据格式：<br>
	 * ticket：获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。<br>
	 * expire_seconds：该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。<br>
	 * url：二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片。<br>
	 * 
	 * @param accessToken
	 * @param expireSeconds	生成的临时二维码有效时间
	 * @param sceneStr 二维码携带的参数
	 * @author TanJin
	 * @date 2015-8-5
	 */
	public static JSONObject createTemporaryQrCodeTicket (String accessToken, int expireSeconds, String sceneStr) {
		//拼接获取二维码ticket的URL链接
		String requestUrl = TICKET_URL.replace("ACCESS_TOKEN", accessToken);
		
		//POST发送数据格式：{"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		JSONObject scene_id = new JSONObject();
		scene_id.put("scene_id", sceneStr);
		
		JSONObject scene = new JSONObject();
		scene.put("scene", scene_id);
		
		JSONObject data = new JSONObject();
		data.put("expire_seconds", expireSeconds);
		data.put("action_name", QR_SCENE);
		data.put("action_info", scene);
		logger.info("[Wechat] [WechatUtils] [createTemporaryQrCode] [requestData:{}]",data.toString());
		
		try {
			JSONObject jsonObject = WechatHttpUtils.doPostToJson(requestUrl, data, null);
			logger.debug("[Wechat] [WechatUtils] [create qr code ticket json:{}]", jsonObject);
			
			if(null != jsonObject){
				if(jsonObject.getIntValue("errcode") == WechatCode.success){
		            return jsonObject;
				} else {
					logger.error("[Wechat] [WechatUtils] [创建临时二维码ticket失败] [errcode:{}] [errmsg:{}]", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
				}
			}else{
				logger.error("[Wechat] [WechatUtils] [ticket json is empty]");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	/**
	 * 组合微信网页授权URL<br>
	 * @param	redirectUrl 跳转URL
	 * @param	appid   开发者申请获取的APPID
	 * @param	scopeType  应用授权作用域  :snsapi_base、snsapi_userinfo 
	 * @param	state	开发者可以填写a-zA-Z0-9的参数值，最多128字节   非必须
	 * @date 2015-12-16
	 */
	public static String buildWechatWebAuthorizationUrl (String appid, String redirectUrl, String scopeType, String state) {
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		
		String requestUrl = WEB_AUTHORIZATION_GET_CODE_URL.replace("APPID", appid).
				replace("REDIRECT_URI", redirectUrl).replace("SCOPE", scopeType).replace("STATE", state);
		
		logger.debug("[Wechat] [WechatUtils] [wechat web authorization get code url:{}]", requestUrl);
		return requestUrl;
	}
	
	/**
	 * 通过code换取网页授权access_token<br>
	 * 网页授权获取openID、access_token、union_id等信息<br>
	 * 
	 * 通过code换取的是一个特殊的网页授权access_token,与基础支持中的access_token（该access_token用于调用其他接口）不同。<br>
	 * 
	 * @param appid	开发者申请获取的APPID
	 * @param appsecret  开发者申请获取的 SECRET 
	 * @param code  换取access_token的票据,只能使用一次
	 * @date 2015-12-16
	 */
	public static JSONObject webAuthorizationGetInfoByCode (String appid, String appsecret, String code) {
		String requestUrl = WEB_AUTHORIZATION_GET_INFO_URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
		try {
			JSONObject jsonObject = (JSONObject) WechatHttpUtils.doGetToJson(requestUrl, null);
			if(null == jsonObject){
				logger.error("[Wechat] [WechatUtils] [web authorization get info is empty]");
			}else{
				if(jsonObject.getIntValue("errcode") != WechatCode.success){
					logger.info("[Wechat] [WechatUtils] [code:{}] [web authorization get info : {}]", code, jsonObject.toString());
					return null;
				}
			}
			return jsonObject;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	/**
	 * 网页授权拉取用户信息，需要scope为snsapi_userinfo
	 * @param webAuthAccessToken 网页授权中获取到的accessToken
	 * @param openid 用户OPENID
	 * @date 2017-1-13
	 */
	public static JSONObject webAuthorizationGetUserInfo(String webAuthAccessToken, String openid) {
		String requestUrl = WEB_AUTHORIZATION_GET_USERINFO_URL.replace("ACCESS_TOKEN", webAuthAccessToken).replace("OPENID", openid);
		try {
			JSONObject jsonObject = (JSONObject) WechatHttpUtils.doGetToJson(requestUrl, null);
			if(null != jsonObject && jsonObject.getIntValue("errcode") == WechatCode.success) {
				return jsonObject;
			} else {
				logger.error("[Wechat] [WechatUtils] [error] [web authorization get userinfo:{}]", jsonObject);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	 /**
     * 长链接转短链接接口<br><br>
     * 
     * 返回数据格式：<br>
     * errcode:错误码，0表示成功<br>
     * errmsg：错误码描述，ok表示成功<br>
     * msgid：消息ID<br>
     * JSON格式：{"errcode":0,"errmsg":"ok","short_url":"http:\/\/w.url.cn\/s\/AvCo6Ih"}<br>
     * 
     * @param accessToken 访问凭证
     * @param longUrl 需要转换的长链接，
     * @date 2015-10-10
     */
    public static String shortUrl(String accessToken, String longUrl) {
    	String requestUrl = WEB_SHORT_URL.replace("ACCESS_TOKEN", accessToken);  

		JSONObject data = new JSONObject();
		data.put("action", "long2short");
		data.put("long_url", longUrl);
		
		logger.debug("[Wechat] [WechatUtils] [shortUrl] [request data:{}]", data.toString());

		try {
			JSONObject jsonObject = WechatHttpUtils.doPostToJson(requestUrl, data, null);
			logger.debug("[Wechat] [WechatUtils] [shortUrl] [response data:{}]", jsonObject);
			
			if(null != jsonObject){
				if(jsonObject.getIntValue("errcode") == WechatCode.success){
		            return jsonObject.getString("short_url");
				} else {
					logger.error("[Wechat] [WechatUtils] [长链接转短链接失败] [errcode:{}] [errmsg:{}]", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
				}
			}else{
				logger.info("[Wechat] [WechatUtils] [shortUrl] [response json is empty]");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
    }
    
    /**
     * 客服接口-发消息<br><br>
     * 
     * 返回数据格式：<br>
     * errcode:错误码，0表示成功<br>
     * errmsg：错误码描述，ok表示成功<br>
     * msgid：消息ID<br>
     * 
     * @param accessToken 访问凭证
     * @param message 推送的模板消息
     * @date 2015-10-10
     */
    public static boolean messageCustomSend (String accessToken, JSONObject message) {
    	String requestUrl = CUSTOM_SEND_MESSAGE.replace("ACCESS_TOKEN", accessToken);  
    	try {
    		JSONObject jsonObject = WechatHttpUtils.doPostToJson(requestUrl, message, null);
    		logger.info("[Wechat] [WechatUtils] [messageCustomSend] [result:{}]", jsonObject.toString());
    		
    		if(null != jsonObject){
        		return jsonObject.getIntValue("errcode") == WechatCode.success;
        	}
		} catch (Exception e) {
			logger.error("", e);
		}
    	return false;
    }
}
