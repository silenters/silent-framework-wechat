package com.silent.framework.wechat.bean;

/**
 * 微信服务号 JSAPI Ticket实体类
 * @author TanJin
 */
public class JsapiTicketBean {

	private String access_token;		//jsapi_ticket accessToken
	private String ticket;				//jsapi_ticket
	private long expires_time;			//失效时间
	private long time;					//获取时间
	private int count = 0;				//请求次数

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
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

	public long getExpires_time() {
		return expires_time;
	}

	public void setExpires_time(long expires_time) {
		this.expires_time = expires_time;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public String toString() {
		return "JsapiTicketBean [access_token=" + access_token + ", ticket="
				+ ticket + ", expires_time=" + expires_time + ", time=" + time
				+ ", count=" + count + "]";
	}
}
