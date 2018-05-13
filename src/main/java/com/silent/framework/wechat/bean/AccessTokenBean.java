package com.silent.framework.wechat.bean;

/**
 * 管理微信服务号 access token实体类
 * @author TanJin
 * @date 2015-8-9
 */
public class AccessTokenBean {

	private String app_id;				//第三方用户唯一凭证
	private String app_secret;			//第三方用户唯一凭证密钥 
	private String access_token;		//access token
	private long expire_time;			//失效时间
	private long time;					//获取时间
	private int count = 0;				//请求次数
	
	public String getApp_id() {
		return app_id;
	}
	
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getApp_secret() {
		return app_secret;
	}

	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public long getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(long expire_time) {
		this.expire_time = expire_time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AccessTokenBean [app_id=" + app_id + ", app_secret="
				+ app_secret + ", access_token=" + access_token
				+ ", expire_time=" + expire_time + ", time=" + time
				+ ", count=" + count + "]";
	}
	
}
