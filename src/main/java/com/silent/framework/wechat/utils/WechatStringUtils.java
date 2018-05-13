package com.silent.framework.wechat.utils;

public class WechatStringUtils {
	/**
	 * 判断字符串是否为空
	 * @date 2015-12-11
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.isEmpty());
	}

	/**
	 * 判断字符串是否不为空
	 * @date 2015-12-11
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null) && (!str.isEmpty());
	}
}
