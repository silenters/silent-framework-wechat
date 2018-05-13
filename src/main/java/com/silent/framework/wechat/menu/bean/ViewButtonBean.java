package com.silent.framework.wechat.menu.bean;

/**
 * 视图按钮-访问网页
 * @author TanJin
 * @date 2015-7-21
 */
public class ViewButtonBean extends ButtonBean {

	private String type;  
    private String url;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ViewButtonBean [type=" + type + ", url=" + url + "]";
	}
}
