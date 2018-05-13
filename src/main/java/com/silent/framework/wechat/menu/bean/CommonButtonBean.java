package com.silent.framework.wechat.menu.bean;

/**
 * 子按钮-简单按钮
 * @author TanJin
 * @date 2015-7-21
 */
public class CommonButtonBean extends ButtonBean {

	private String type;  
    private String key;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return "CommonButtonBean [type=" + type + ", key=" + key + "]";
	}  
}
