package com.silent.framework.wechat.utils.wechat;

/**
 * 常量集合
 * @date 2015-7-21
 */
public class WechatConstant {
	
	public static String app_id;			//微信APP_ID
	public static String app_secret;		//微信APP_SECRET
	public static String token;				//接口调试token
	
	/** 
     * 返回消息类型：文本 
     */  
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * 返回消息类型：图文 
     */  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
  
    /** 
     * 请求消息类型：文本 
     */  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * 请求消息类型：音频 
     */  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /** 
     * 请求消息类型：推送 
     */  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
  
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
    
    /**
     * 事件按类型：用户已关注时的事件推送
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK"; 
    
    /**
     * 时间类型：LOCATION(获取用户地理位置)
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    
	public void setApp_id(String app_id) {
		WechatConstant.app_id = app_id;
	}
	public void setApp_secret(String app_secret) {
		WechatConstant.app_secret = app_secret;
	}
	public void setToken(String token) {
		WechatConstant.token = token;
	}
}

