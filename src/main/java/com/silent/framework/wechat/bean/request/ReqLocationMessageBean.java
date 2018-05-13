package com.silent.framework.wechat.bean.request;

/**
 * 地理位置消息
 * @date 2015-7-21
 */
public class ReqLocationMessageBean extends ReqBaseMessageBean {

	// 地理位置维度  
    private String Location_X;  
    // 地理位置经度  
    private String Location_Y;  
    // 地图缩放大小  
    private String Scale;  
    // 地理位置信息  
    private String Label;
    
    public ReqLocationMessageBean(){
		
	}
	
	public ReqLocationMessageBean(ReqBaseMessageBean reqBaseMessageBean) {
		this.CreateTime = reqBaseMessageBean.CreateTime;
		this.FromUserName = reqBaseMessageBean.FromUserName;
		this.ToUserName = reqBaseMessageBean.ToUserName;
		this.MsgId = reqBaseMessageBean.getMsgId();
		this.MsgType = reqBaseMessageBean.getMsgType();
	}
    
	public String getLocation_X() {
		return Location_X;
	}
	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}
	public String getLocation_Y() {
		return Location_Y;
	}
	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}

	@Override
	public String toString() {
		return "ReqLocationMessageBean [Location_X=" + Location_X
				+ ", Location_Y=" + Location_Y + ", Scale=" + Scale
				+ ", Label=" + Label + ", getToUserName()=" + getToUserName()
				+ ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getMsgId()=" + getMsgId() + "]";
	}
	
}
