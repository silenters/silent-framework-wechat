package com.silent.framework.wechat.bean.request;

/**
 * 文本消息
 * @date 2015-7-21
 */
public class ReqTextMessageBean extends ReqBaseMessageBean {

	// 消息内容  
    private String Content;
    
    public ReqTextMessageBean(){
		
	}
	
	public ReqTextMessageBean(ReqBaseMessageBean reqBaseMessageBean) {
		this.CreateTime = reqBaseMessageBean.CreateTime;
		this.FromUserName = reqBaseMessageBean.FromUserName;
		this.ToUserName = reqBaseMessageBean.ToUserName;
		this.MsgId = reqBaseMessageBean.getMsgId();
		this.MsgType = reqBaseMessageBean.getMsgType();
	}

	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "ReqTextMessageBean [Content=" + Content + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getMsgId()=" + getMsgId() + "]";
	}
}
