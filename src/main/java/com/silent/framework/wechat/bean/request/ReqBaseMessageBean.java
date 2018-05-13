package com.silent.framework.wechat.bean.request;

import com.silent.framework.base.utils.StringUtils;

/**
 * 消息基类（普通用户 -> 公众帐号）
 * @date 2015-7-21
 */
public class ReqBaseMessageBean {

	// 开发者微信号  
	protected String ToUserName;  
    // 发送方帐号（一个OpenID）  
    protected String FromUserName;  
    // 消息创建时间 （整型）  
    protected long CreateTime;  
    // 消息类型（text/image/location/link）  
    protected String MsgType;  
    // 消息id，64位整型  
    protected long MsgId;
    
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = StringUtils.parseLong(msgId, 0);
	}
	
	@Override
	public String toString() {
		return "ReqBaseMessageBean [ToUserName=" + ToUserName
				+ ", FromUserName=" + FromUserName + ", CreateTime="
				+ CreateTime + ", MsgType=" + MsgType + ", MsgId=" + MsgId
				+ "]";
	}  
}
