package com.silent.framework.wechat.menu.bean;

/**
 * 按钮基类
 * @author TanJin
 * @date 2015-7-21
 */
public class ButtonBean {

	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "ButtonBean [name=" + name + "]";
	}
}
