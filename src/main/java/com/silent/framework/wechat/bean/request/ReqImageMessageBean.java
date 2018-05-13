package com.silent.framework.wechat.bean.request;

/**
 * 图片消息
 * @date 2015-7-21
 */
public class ReqImageMessageBean extends ReqBaseMessageBean {

	// 图片链接  
    private String PicUrl;
    
    public ReqImageMessageBean(){
		
	}
	
	public ReqImageMessageBean(ReqBaseMessageBean reqBaseMessageBean) {
		this.CreateTime = reqBaseMessageBean.CreateTime;
		this.FromUserName = reqBaseMessageBean.FromUserName;
		this.ToUserName = reqBaseMessageBean.ToUserName;
		this.MsgId = reqBaseMessageBean.getMsgId();
		this.MsgType = reqBaseMessageBean.getMsgType();
	}

	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	@Override
	public String toString() {
		return "ReqImageMessageBean [PicUrl=" + PicUrl + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getMsgId()=" + getMsgId() + "]";
	}
	
}
