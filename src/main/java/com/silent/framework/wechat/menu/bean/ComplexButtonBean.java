package com.silent.framework.wechat.menu.bean;

import java.util.Arrays;

/**
 * 父按钮-复杂按钮
 * @author TanJin
 * @date 2015-7-21
 */
public class ComplexButtonBean extends ButtonBean {

	private ButtonBean[] sub_button;

	public ButtonBean[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(ButtonBean[] sub_button) {
		this.sub_button = sub_button;
	}
	
	@Override
	public String toString() {
		return "ComplexButtonBean [sub_button=" + Arrays.toString(sub_button) + "]";
	}  
}
