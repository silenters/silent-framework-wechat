package com.silent.framework.wechat.bean.response;

/**
 * 文本消息
 * @date 2015-7-21
 */
public class RespTextMessageBean extends RespBaseMessageBean {

	// 回复的消息内容  
    private String Content;

	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	
	@Override
	public String toString() {
		return "RespTextMessageBean [Content=" + Content + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getFuncFlag()=" + getFuncFlag() + "]";
	}
}
