package com.silent.framework.wechat.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silent.framework.wechat.menu.bean.ButtonBean;
import com.silent.framework.wechat.menu.bean.ComplexButtonBean;
import com.silent.framework.wechat.menu.bean.MenuBean;
import com.silent.framework.wechat.menu.bean.ViewButtonBean;
import com.silent.framework.wechat.menu.util.MenuUtil;
import com.silent.framework.wechat.service.AccessTokenService;
import com.silent.framework.wechat.utils.wechat.WechatConstant;

public class MenuTest {
	private static Logger log = LoggerFactory.getLogger(MenuTest.class);
	
	public static void main(String[] args) {
		WechatConstant wechatConstant = new WechatConstant();
		wechatConstant.setApp_id("wx2a5c4a5d7efe5497");
		wechatConstant.setApp_secret("7d0a02a6f3c9cfb2110c0203cdcf372d");
		String accessToken = AccessTokenService.getInstance().queryAccessToken();
		
		int result = MenuUtil.createMenu(getMenu(WechatConstant.app_id), accessToken);  
		  
        // 判断菜单创建结果  
        if (0 == result)  
            log.info("服务号菜单创建成功！");  
        else  
            log.info("服务号菜单创建失败，错误码：" + result);  
	}
	
	private static MenuBean getMenu(String appId) {  
        
        ViewButtonBean btn4 = new ViewButtonBean();  
        btn4.setName("激活设备");  
        btn4.setType("view");  
        btn4.setUrl("http://backend.stage.hipee.cn/hipeeweb/web/device/wifi"); 
        
        ViewButtonBean btn5 = new ViewButtonBean();  
        btn5.setName("首页");  
        btn5.setType("view");  
        btn5.setUrl("http://backend.stage.hipee.cn/hipeeweb/index/HiPee.jsp"); 
        
        ComplexButtonBean mainBtn1 = new ComplexButtonBean();  
        mainBtn1.setName("菜单1");  
        mainBtn1.setSub_button(new ButtonBean[] {btn4, btn5});  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        MenuBean menu = new MenuBean();  
        menu.setButton(new ButtonBean[] { mainBtn1});  
        return menu;  
    }
}
