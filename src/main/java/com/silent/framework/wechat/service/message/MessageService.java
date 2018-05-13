package com.silent.framework.wechat.service.message;

import java.util.List;

import com.silent.framework.wechat.bean.request.ReqBaseMessageBean;
import com.silent.framework.wechat.bean.response.Article;
import com.silent.framework.wechat.bean.response.RespNewsMessageBean;
import com.silent.framework.wechat.bean.response.RespTextMessageBean;
import com.silent.framework.wechat.utils.wechat.WechatConstant;

/**
 * 消息处理Service
 * 
 * 该类封装了一些服务号返回给用户的消息格式信息，可直接获取要返回给用户的数据
 * 
 * @author TanJin
 * @date 2016-10-14
 */
public class MessageService {
	private static MessageService instance = new MessageService();
	private MessageService() {}
	public static MessageService getInstance() {
		return instance;
	}
	
	/**
	 * 回传给微信服务号文本信息
	 * @date 2015-8-10
	 */
	public RespTextMessageBean toResponseTextMessage(String text, ReqBaseMessageBean reqBaseMessageBean){
		RespTextMessageBean respTextMessageBean = new RespTextMessageBean();
		respTextMessageBean.setCreateTime(System.currentTimeMillis()/1000);
		respTextMessageBean.setToUserName(reqBaseMessageBean.getFromUserName());
		respTextMessageBean.setFromUserName(reqBaseMessageBean.getToUserName());
		respTextMessageBean.setMsgType(WechatConstant.RESP_MESSAGE_TYPE_TEXT);
		respTextMessageBean.setContent(text);
		respTextMessageBean.setFuncFlag(0);
		return respTextMessageBean;
	}
	
	/**
	 * 回传给微信服务器图文信息
	 * @date 2015-9-25
	 */
	public RespNewsMessageBean toResponseNewsMessage(List<Article> articleList, ReqBaseMessageBean reqBaseMessageBean){
		RespNewsMessageBean respNewsMessageBean = new RespNewsMessageBean();
		respNewsMessageBean.setArticleCount(articleList.size());
		respNewsMessageBean.setArticles(articleList);
		respNewsMessageBean.setCreateTime(System.currentTimeMillis()/1000);
		respNewsMessageBean.setToUserName(reqBaseMessageBean.getFromUserName());
		respNewsMessageBean.setFromUserName(reqBaseMessageBean.getToUserName());
		respNewsMessageBean.setMsgType(WechatConstant.RESP_MESSAGE_TYPE_NEWS);
		return respNewsMessageBean;
	}
}
