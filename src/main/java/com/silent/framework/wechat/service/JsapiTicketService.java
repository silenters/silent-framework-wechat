package com.silent.framework.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.silent.framework.wechat.bean.JsapiTicketBean;
import com.silent.framework.wechat.utils.WechatStringUtils;
import com.silent.framework.wechat.utils.wechat.WechatUtils;

/**
 * 管理微信服务号 JSAPI Ticket实体类
 * @author TanJin 
 * @date 2017-1-20
 */
public class JsapiTicketService {
	private static final Logger logger = LoggerFactory.getLogger(JsapiTicketService.class);
	private JsapiTicketBean jsapiTicketBean;
	
	private static JsapiTicketService instance = new JsapiTicketService();
	private JsapiTicketService() {}
	public static JsapiTicketService getInstance() {
		return instance;
	}
	
	private void reload() {
		int count = 5;
		while(count > 0) {
			String accessToken = AccessTokenService.getInstance().queryAccessToken();		//获取accessToken
			if(WechatStringUtils.isEmpty(accessToken)) {
				count--;
				logger.error("[wechat] [jsapiTicketService] [query access tokan error, access token is empty]");
				continue;
			}
			
			JSONObject json = WechatUtils.getJsTicket(accessToken);
			if(json != null && json.getInteger("errcode") == 0) {
				if(null == jsapiTicketBean) {
					jsapiTicketBean = new JsapiTicketBean();
				}
				jsapiTicketBean.setAccess_token(accessToken);
				jsapiTicketBean.setTicket(json.getString("ticket"));
				
				long now = System.currentTimeMillis();
				long expiresIn = 60*60*1000L;	//本地服务器设置到期时间为1小时
				
				jsapiTicketBean.setTime(now);
				jsapiTicketBean.setExpires_time(now + expiresIn);
				jsapiTicketBean.setCount(jsapiTicketBean.getCount() + 1);
				count = 0;
			} else {
				count--;
			}
		}
		logger.info("[wechat] [jsapiTicketService] [JsapiTicketBean] [operation_time:{}] [{}]", System.currentTimeMillis(), jsapiTicketBean);
	}
	
	/**
	 * 获取JsapiTicketBean
	 * @date 2016-10-14
	 */
	public JsapiTicketBean queryJsapiTicketBean() {
		if(jsapiTicketBean == null){
			reload();
		}
		//若超过超时时间，则重新获取
		if(System.currentTimeMillis() > jsapiTicketBean.getExpires_time()) {
			reload();
		}
		return jsapiTicketBean;
	}
	
	/**
	 * 获取微信JsapiTicket
	 * @date 2016-9-16
	 */
	public String queryJsapiTicket() {
		if(jsapiTicketBean == null){
			reload();
		}
		//若超过超时时间，则重新获取
		if(System.currentTimeMillis() > jsapiTicketBean.getExpires_time()) {
			reload();
		}
		logger.debug("[wechat] [access_token:{}]", jsapiTicketBean.getAccess_token());
		return jsapiTicketBean.getTicket();
	}
	
	/**
	 * 如果JsapiTicket已经失效，则重新获取微信JsapiTicket
	 * @date 2016-9-16
	 */
	public String queryJsapiTicketIfTokenFilure() {
		reload();
		logger.debug("[wechat] [jsapi_ticket:{}]", jsapiTicketBean.getTicket());
		return jsapiTicketBean.getTicket();
	}
}
