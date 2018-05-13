package com.silent.framework.wechat.test;

import com.alibaba.fastjson.JSONObject;
import com.silent.framework.wechat.service.AccessTokenService;
import com.silent.framework.wechat.utils.wechat.WechatConstant;
import com.silent.framework.wechat.utils.wechat.WechatUtils;

/**
 * 测试获取带参数二维码信息，没事别乱用。。会影响服务号运行
 * @author TanJin
 * @date 2017年7月13日
 */
public class QrCodeTest {

	public static void main(String[] args) {
		
		WechatConstant wechatConstant = new WechatConstant();
		wechatConstant.setApp_id("wx2a5c4a5d7efe5497");
		wechatConstant.setApp_secret("7d0a02a6f3c9cfb2110c0203cdcf372d");
		String accessToken = AccessTokenService.getInstance().queryAccessToken();
		
		JSONObject jsonObject = WechatUtils.createPermanentQrCodeTicket(accessToken, "33345666743323");
		System.out.println(jsonObject);
	}
}
