package com.silent.framework.wechat.menu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.silent.framework.wechat.menu.bean.MenuBean;
import com.silent.framework.wechat.utils.http.WechatHttpUtils;

/**
 * 微信公众号/服务号生成菜单工具类
 * @author TanJin 
 * @date 2016-9-15
 */
public class MenuUtil {
	protected static Logger logger = LoggerFactory.getLogger(MenuUtil.class);

	// 菜单创建（POST） 限100（次/天）  
	private static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//删除菜单
	private static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/** 
	 * 创建菜单 
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(MenuBean menu, String accessToken) {  
	    int result = 0;  
	  
	    // 拼装创建菜单的URL  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成JSON字符串  
	    String jsonMenu = JSON.toJSON(menu).toString();  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = null;
		try {
			jsonObject = WechatHttpUtils.doPostToJson(url, JSON.parseObject(jsonMenu), null);
		} catch (Exception e) {
			logger.error("", e);
		}
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getIntValue("errcode")) {  
	            result = jsonObject.getIntValue("errcode");  
	            logger.error("创建菜单失败 errcode:{} errmsg:{}", 
	            		jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return result;  
	}

	/** 
	 * 删除菜单 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */ 
	public static int deleteMenu(String accessToken) {
		int result = 0; 
		
		// 拼装删除菜单的URL  
	    String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);  
	    // 调用接口创建菜单  
	    JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) WechatHttpUtils.doGetToJson(url, null);
		} catch (Exception e) {
			logger.error("", e);
		}
	    if (null != jsonObject) {  
	        if (0 != jsonObject.getIntValue("errcode")) {  
	            result = jsonObject.getIntValue("errcode");  
	            logger.error("删除菜单失败 errcode:{} errmsg:{}", 
	            		jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
	    return result;  
	}
}
