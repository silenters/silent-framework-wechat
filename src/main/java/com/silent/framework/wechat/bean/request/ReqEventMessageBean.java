package com.silent.framework.wechat.bean.request;

/**
 * 事件
 * @date 2015-7-21
 */
public class ReqEventMessageBean extends ReqBaseMessageBean {

	private String event;		//事件类型
	private String eventKey;	//事件KEY值
	
	public ReqEventMessageBean(){
		
	}
	
	public ReqEventMessageBean(ReqBaseMessageBean reqBaseMessageBean) {
		this.CreateTime = reqBaseMessageBean.CreateTime;
		this.FromUserName = reqBaseMessageBean.FromUserName;
		this.ToUserName = reqBaseMessageBean.ToUserName;
		this.MsgId = reqBaseMessageBean.getMsgId();
		this.MsgType = reqBaseMessageBean.getMsgType();
	}
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@Override
	public String toString() {
		return "ReqEventMessageBean [event=" + event + ", eventKey=" + eventKey
				+ ", getToUserName()=" + getToUserName()
				+ ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getMsgId()=" + getMsgId() + "]";
	}
}
