package com.silent.framework.wechat.bean.request;

/**
 * 微信推送的用户地理位置信息实体类
 * @author TanJin
 * @date 2015-9-1
 */
public class ReqEventLocationMessageBean extends ReqBaseMessageBean {


	// 地理位置维度  
    private String Latitude;  
    // 地理位置经度  
    private String Longitude;  
    // 地理位置精度  
    private String Precision;  
    
    public ReqEventLocationMessageBean(){
		
	}
	
	public ReqEventLocationMessageBean(ReqBaseMessageBean reqBaseMessageBean) {
		this.CreateTime = reqBaseMessageBean.CreateTime;
		this.FromUserName = reqBaseMessageBean.FromUserName;
		this.ToUserName = reqBaseMessageBean.ToUserName;
		this.MsgId = reqBaseMessageBean.getMsgId();
		this.MsgType = reqBaseMessageBean.getMsgType();
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}

	@Override
	public String toString() {
		return "ReqEventLocationMessageBean [Latitude=" + Latitude
				+ ", Longitude=" + Longitude + ", Precision=" + Precision
				+ ", getToUserName()=" + getToUserName()
				+ ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getMsgId()=" + getMsgId() + "]";
	}
}
