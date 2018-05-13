package com.silent.framework.wechat.bean.response;

/**
 * 音乐消息
 * @date 2017-1-12
 */
public class RespMusicMessageBean extends RespBaseMessageBean {

	// 音乐  
    private Music Music;

	public Music getMusic() {
		return Music;
	}
	public void setMusic(Music music) {
		Music = music;
	}
	
	@Override
	public String toString() {
		return "RespMusicMessageBean [Music=" + Music + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getFuncFlag()=" + getFuncFlag() + "]";
	}
	
}
