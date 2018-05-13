package com.silent.framework.wechat.menu.main;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silent.framework.wechat.menu.bean.ButtonBean;
import com.silent.framework.wechat.menu.bean.CommonButtonBean;
import com.silent.framework.wechat.menu.bean.ComplexButtonBean;
import com.silent.framework.wechat.menu.bean.MenuBean;
import com.silent.framework.wechat.menu.bean.ViewButtonBean;
import com.silent.framework.wechat.menu.util.MenuUtil;
import com.silent.framework.wechat.service.AccessTokenService;
import com.silent.framework.wechat.utils.WechatStringUtils;
import com.silent.framework.wechat.utils.wechat.WechatConstant;
import com.silent.framework.wechat.utils.wechat.WechatUtils;

/**
 * 菜单管理器类
 * @date 2015-7-21
 */
public class MenuManager {

	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	/**
	 * 创建服务号菜单，运行此main方法即可
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		WechatConstant constant = new WechatConstant();
		constant.setApp_id("APPID");
		constant.setApp_secret("APPSECRET");
		handleMenu();
    }
	
	/**
	 * 生成服务号菜单
	 * @date 2016-9-7
	 */
	private static void handleMenu() {
        String accessToken = AccessTokenService.getInstance().queryAccessToken();
        
        if (WechatStringUtils.isNotEmpty(accessToken)) {  
            // 调用接口创建菜单  
            int result = MenuUtil.createMenu(getMenu(WechatConstant.app_id), accessToken);  
  
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("服务号菜单创建成功！");  
            else  
                log.info("服务号菜单创建失败，错误码：" + result);  
        }
	}
	
	
	/** 
     * 组装菜单数据 
     * @param serverType 服务器类型
     * @return 
     */  
    private static MenuBean getMenu(String appId) {  
    	String userAuthUrl = WechatUtils.buildWechatWebAuthorizationUrl(appId,
    			"http://stage.3gmimo.com/weixin/webauth/userinfo.do", WechatUtils.SCOPE_SNSAPI_USERINFO, "vlife");
    	
    	String userSilentAuthUrl = WechatUtils.buildWechatWebAuthorizationUrl(appId,
    			"http://stage.3gmimo.com/weixin/webauth/silent.do", WechatUtils.SCOPE_SNSAPI_USERINFO, "vlife");
    	
        CommonButtonBean btn1 = new CommonButtonBean();  
        btn1.setName("单个图文消息");  
        btn1.setType("click");  
        btn1.setKey("10");  
        
        CommonButtonBean btn2 = new CommonButtonBean();  
        btn2.setName("多个图文消息");  
        btn2.setType("click");  
        btn2.setKey("11");
        
        CommonButtonBean btn3 = new CommonButtonBean();  
        btn3.setName("音乐消息");  
        btn3.setType("click");  
        btn3.setKey("12");
        
        ViewButtonBean btn4 = new ViewButtonBean();  
        btn4.setName("用户网页授权");  
        btn4.setType("view");  
        btn4.setUrl(userAuthUrl); 
        
        ViewButtonBean btn5 = new ViewButtonBean();  
        btn5.setName("用户静默网页授权");  
        btn5.setType("view");  
        btn5.setUrl(userSilentAuthUrl); 
        
        ComplexButtonBean mainBtn1 = new ComplexButtonBean();  
        mainBtn1.setName("菜单1");  
        mainBtn1.setSub_button(new ButtonBean[] {btn1, btn2, btn3});  
  
        ComplexButtonBean mainBtn2 = new ComplexButtonBean();  
        mainBtn2.setName("菜单2");  
        mainBtn2.setSub_button(new ButtonBean[] {btn4, btn5});  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        MenuBean menu = new MenuBean();  
        menu.setButton(new ButtonBean[] { mainBtn1, mainBtn2});  
        return menu;  
    }
}
