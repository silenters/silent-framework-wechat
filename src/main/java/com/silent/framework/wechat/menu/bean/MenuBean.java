package com.silent.framework.wechat.menu.bean;

import java.util.Arrays;

/**
 * 菜单
 * @author TanJin
 * @date 2015-7-21
 */
public class MenuBean {

	private ButtonBean[] button;

	public ButtonBean[] getButton() {
		return button;
	}
	public void setButton(ButtonBean[] button) {
		this.button = button;
	}
	@Override
	public String toString() {
		return "MenuBean [button=" + Arrays.toString(button) + "]";
	}
}
