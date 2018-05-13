package com.silent.framework.wechat.bean.request;

/**
 * 音频消息
 * @date 2015-7-21
 */
public class ReqVoiceMessageBean extends ReqBaseMessageBean {

	// 媒体ID  
    private String MediaId;  
    // 语音格式  
    private String Format;
    
    public ReqVoiceMessageBean(){
		
	}
	
	public ReqVoiceMessageBean(ReqBaseMessageBean reqBaseMessageBean) {
		this.CreateTime = reqBaseMessageBean.CreateTime;
		this.FromUserName = reqBaseMessageBean.FromUserName;
		this.ToUserName = reqBaseMessageBean.ToUserName;
		this.MsgId = reqBaseMessageBean.getMsgId();
		this.MsgType = reqBaseMessageBean.getMsgType();
	}
    
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}

	@Override
	public String toString() {
		return "ReqVoiceMessageBean [MediaId=" + MediaId + ", Format=" + Format
				+ ", getToUserName()=" + getToUserName()
				+ ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getMsgId()=" + getMsgId() + "]";
	}
}
