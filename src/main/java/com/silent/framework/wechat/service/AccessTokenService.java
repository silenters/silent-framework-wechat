package com.silent.framework.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.silent.framework.wechat.bean.AccessTokenBean;
import com.silent.framework.wechat.utils.wechat.WechatConstant;
import com.silent.framework.wechat.utils.wechat.WechatUtils;

/**
 * 微信 AccessToken管理Service
 * @author TanJin 
 * @date 2016-9-15
 */
public class AccessTokenService {
	private static final Logger logger = LoggerFactory.getLogger(AccessTokenService.class);
	private AccessTokenBean accessTokenBean;
	
	private static AccessTokenService instance = new AccessTokenService();
	private AccessTokenService() {}
	public static AccessTokenService getInstance() {
		return instance;
	}
	
	private void reload() {
		int count = 5;
		while(count > 0) {
  			JSONObject json = WechatUtils.getAccessToken(WechatConstant.app_id, WechatConstant.app_secret);
			if(null != json) {
				if(null == accessTokenBean) {
					accessTokenBean = new AccessTokenBean();
				}
				accessTokenBean.setApp_id(WechatConstant.app_id);
				accessTokenBean.setApp_secret(WechatConstant.app_secret);
				accessTokenBean.setAccess_token(json.getString("access_token"));
				
				long now = System.currentTimeMillis();
				long expiresIn = 60*60*1000L;	//本地服务器设置到期时间为1小时
				
				accessTokenBean.setTime(now);
				accessTokenBean.setExpire_time(now + expiresIn);
				accessTokenBean.setCount(accessTokenBean.getCount() + 1);
				count = 0;
			} else {
				count--;
			}
		}
		logger.info("[Wechat] [AccessToken] [operationTime:{}] [{}]", System.currentTimeMillis(), accessTokenBean);
	}
	
	/**
	 * 获取accessTokenBena
	 * @date 2016-10-14
	 */
	public AccessTokenBean queryAccessTokenBena() {
		if(null == accessTokenBean) {
			reload();
		}
		//若超过超时时间，则重新获取
		if(System.currentTimeMillis() > accessTokenBean.getExpire_time()) {
			reload();
		}
		logger.debug("[Wechat] [AccessTokenBean:{}]", accessTokenBean);
		return accessTokenBean;
	}
	
	/**
	 * 获取微信AccessToken
	 * @date 2016-9-16
	 */
	public String queryAccessToken() {
		//若未初始化，则先初始化
		if(null == accessTokenBean) {
			this.reload();
		}
		//若超过超时时间，则重新获取
		if(System.currentTimeMillis() > accessTokenBean.getExpire_time()) {
			reload();
		}
		logger.debug("[Wechat] [accessToken:{}]", accessTokenBean.getAccess_token());
		return accessTokenBean.getAccess_token();
	}
	
	/**
	 * 如果AccessToken已经失效，则重新获取微信AccessToken
	 * @date 2016-9-16
	 */
	public String queryAccessTokenReload() {
		reload();
		logger.debug("[Wechat] [Reload] [accessToken:{}]", accessTokenBean.getAccess_token());
		return accessTokenBean.getAccess_token();
	}
}
