package com.silent.framework.wechat.service.message;

import com.silent.framework.wechat.bean.request.ReqBaseMessageBean;
import com.silent.framework.wechat.bean.response.RespBaseMessageBean;

/**
 * 消息处理service
 * @author TanJin
 * @date 2016-9-16
 */
public interface IMessageService {

	/**
	 * 根据客户端信息进行处理并获取返回给客户端的信息
	 * @date 2015-7-21
	 */
	public RespBaseMessageBean handleResponse(ReqBaseMessageBean reqBaseMessageBean) throws Exception;
}
