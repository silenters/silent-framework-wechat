package com.silent.framework.wechat.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silent.framework.wechat.service.message.IMessageService;

/**
 * 微信具体消息处理类工厂
 * @author TanJin
 * @date 2016-9-16
 */
public class ServiceFactory {
	private static final Logger logger = LoggerFactory.getLogger(JsapiTicketService.class);
	private static ServiceFactory instance = new ServiceFactory();
	private ServiceFactory() {}
	public static ServiceFactory getInstance() {
		return instance;
	}
	
	private Map<String, IMessageService> map = new HashMap<String, IMessageService>();

	public Map<String, IMessageService> getMap() {
		return map;
	}
	public void setMap(Map<String, IMessageService> map) {
		instance.map = map;
	}
	
	public IMessageService createService(String type){
		IMessageService service = map.get(type);
		logger.debug("[wechat] [type:{}] [service:{}]", type, service);
		return service;
	}
}
