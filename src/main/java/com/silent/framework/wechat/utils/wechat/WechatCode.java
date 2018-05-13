package com.silent.framework.wechat.utils.wechat;

/**
 * 常见到的微信服务号 响应信息全局返回码解释<br>
 * 所有全局返回码说明：http://mp.weixin.qq.com/wiki/10/6380dc743053a91c544ffd2b7c959166.html<br>
 * @author TanJin 
 * @date 2017-1-13
 */
public class WechatCode {
	
	/**
	 * 请求成功
	 */
	public static final int success = 0;

	/**
	 * 系统繁忙，稍后再试
	 */
	public static final int error_system_busy = -1;
	
	/**
	 * 获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口
	 */
	public static final int error_access_token_invalid = 40001;
	
	/**
	 * 不合法的凭证类型
	 */
	public static final int error_illegal_certificate = 40002;

	/**
	 * 不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID
	 */
	public static final int error_illegal_openid = 40003;
	
	/**
	 * 不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写
	 */
	public static final int error_illegal_appid = 40013;
	
	/**
	 * 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口
	 */
	public static final int error_illegal_access_token = 40014;
	
	/**
	 * 不合法的菜单类型
	 */
	public static final int error_illegal_menu_type = 40015;
	
	/**
	 * access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明
	 */
	public static final int error_access_token_timeout = 42001;
}
