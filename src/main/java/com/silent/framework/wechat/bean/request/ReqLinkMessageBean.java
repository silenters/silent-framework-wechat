package com.silent.framework.wechat.bean.request;

/**
 * 链接消息 
 * @date 2015-7-21
 */
public class ReqLinkMessageBean extends ReqBaseMessageBean {

	// 消息标题  
    private String Title;  
    // 消息描述  
    private String Description;  
    // 消息链接  
    private String Url;
    
    public ReqLinkMessageBean(){
		
	}
	
	public ReqLinkMessageBean(ReqBaseMessageBean reqBaseMessageBean) {
		this.CreateTime = reqBaseMessageBean.CreateTime;
		this.FromUserName = reqBaseMessageBean.FromUserName;
		this.ToUserName = reqBaseMessageBean.ToUserName;
		this.MsgId = reqBaseMessageBean.getMsgId();
		this.MsgType = reqBaseMessageBean.getMsgType();
	}
    
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}

	@Override
	public String toString() {
		return "ReqLinkMessageBean [Title=" + Title + ", Description="
				+ Description + ", Url=" + Url + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getMsgId()=" + getMsgId() + "]";
	}
	
}
