package com.silent.framework.wechat.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silent.framework.base.collection.QueueMap;
import com.silent.framework.base.utils.StringUtils;
import com.silent.framework.wechat.bean.request.ReqBaseMessageBean;
import com.silent.framework.wechat.bean.request.ReqEventLocationMessageBean;
import com.silent.framework.wechat.bean.request.ReqEventMessageBean;
import com.silent.framework.wechat.bean.request.ReqImageMessageBean;
import com.silent.framework.wechat.bean.request.ReqLinkMessageBean;
import com.silent.framework.wechat.bean.request.ReqLocationMessageBean;
import com.silent.framework.wechat.bean.request.ReqTextMessageBean;
import com.silent.framework.wechat.bean.request.ReqVoiceMessageBean;
import com.silent.framework.wechat.bean.response.RespBaseMessageBean;
import com.silent.framework.wechat.bean.response.RespMusicMessageBean;
import com.silent.framework.wechat.bean.response.RespNewsMessageBean;
import com.silent.framework.wechat.bean.response.RespTextMessageBean;
import com.silent.framework.wechat.exception.UnknownMessageTypeException;
import com.silent.framework.wechat.service.message.IMessageService;
import com.silent.framework.wechat.utils.wechat.MessageUtil;
import com.silent.framework.wechat.utils.wechat.WechatConstant;

/**
 * 核心业务处理类
 * @date 2015-7-21
 */
public class CoreService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static CoreService instance = new CoreService();
	private CoreService() {}
	public static CoreService getInstance() {
		return instance;
	}
	
	//缓存用户请求记录
	private Map<String, ReqBaseMessageBean> map = new QueueMap<String, ReqBaseMessageBean>(10000);
		
	/** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public String processRequest(HttpServletRequest request) {  
        String respMessage = null;  
        try {  
            // XML请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
            logger.debug("[wechat] [CoreService] [request] [{}]", requestMap);
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");
            //消息ID
            String msgId = requestMap.get("MsgId");
            
            RespBaseMessageBean respBaseMessageBean = null;
  
            // 接收到的消息
            ReqBaseMessageBean reqBaseMessageBean = new ReqBaseMessageBean();
            reqBaseMessageBean.setToUserName(toUserName);
            reqBaseMessageBean.setFromUserName(fromUserName);
            reqBaseMessageBean.setCreateTime(new Date().getTime());
            reqBaseMessageBean.setMsgType(msgType);
            reqBaseMessageBean.setMsgId(msgId);
            
            logger.debug("[wechat] [CoreService] [ReqBaseMessageBean:{}] [sessionMapSize:{}]", reqBaseMessageBean, map.size());
  
            //判断请求是否重复，若重复则不进行处理直接返回不响应
            boolean isRepeat = checkRepeat(reqBaseMessageBean);
            if(isRepeat) {
            	return "";
            }
            
            // 文本消息  
            if (WechatConstant.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {  
            	respBaseMessageBean = handleText(requestMap,reqBaseMessageBean);
            }  
            // 图片消息  
            else if (WechatConstant.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {  
            	respBaseMessageBean = handleImage(requestMap, reqBaseMessageBean);
            }
            // 地理位置消息  
            else if (WechatConstant.REQ_MESSAGE_TYPE_LOCATION.equals(msgType)) {  
            	respBaseMessageBean = handleLocation(requestMap,reqBaseMessageBean);
            }  
            // 链接消息  
            else if (WechatConstant.REQ_MESSAGE_TYPE_LINK.equals(msgType)) {  
            	respBaseMessageBean = handleLink(requestMap,reqBaseMessageBean);
            }  
            // 音频消息  
            else if (WechatConstant.REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {  
            	respBaseMessageBean = handleVoice(requestMap,reqBaseMessageBean);
            }  
            // 事件推送  
            else if (WechatConstant.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {  
            	respBaseMessageBean = handleEvent(requestMap,reqBaseMessageBean);
            }
            //未知类型
            else {
            	throw new UnknownMessageTypeException();
            }  
            
            if(respBaseMessageBean instanceof RespTextMessageBean){
            	respMessage = MessageUtil.textMessageToXml((RespTextMessageBean)respBaseMessageBean); 
            	//空串不做回复
            	if(StringUtils.isEmpty(((RespTextMessageBean)respBaseMessageBean).getContent())){
            		respMessage = "";
            	}
            }else if(respBaseMessageBean instanceof RespMusicMessageBean){
            	respMessage = MessageUtil.musicMessageToXml((RespMusicMessageBean)respBaseMessageBean);
            }else if(respBaseMessageBean instanceof RespNewsMessageBean){
            	respMessage = MessageUtil.newsMessageToXml((RespNewsMessageBean)respBaseMessageBean);
            }
            
        } catch (Exception e) {  
           logger.error("", e);
        }  
  
        logger.debug("[wechat] [CoreService] [response] [{}]", respMessage);
        return respMessage;  
    }
    
    /**
     * 判断请求是否重复
     * @param 
     * @return
     * @date 2017年7月25日
     */
    private boolean checkRepeat(ReqBaseMessageBean reqBaseMessageBean) {
    	//用户上次请求
		ReqBaseMessageBean lastMessageBean = map.get(reqBaseMessageBean.getFromUserName());
		if(null == lastMessageBean) {
			map.put(reqBaseMessageBean.getFromUserName(), reqBaseMessageBean);
			return false;
		}
    	//文本消息
		if(WechatConstant.REQ_MESSAGE_TYPE_TEXT.equals(reqBaseMessageBean.getMsgType())) {
			//判断上次请求是否为文本消息
			if(WechatConstant.REQ_MESSAGE_TYPE_TEXT.equals(lastMessageBean.getMsgType())) {
				if(reqBaseMessageBean.getMsgId() == lastMessageBean.getMsgId()) {
					logger.info("[CoreService] [openid:{}] [Repeat]", reqBaseMessageBean.getFromUserName());
					return true;
				}
			}
		} else {
			if(reqBaseMessageBean.getCreateTime() == lastMessageBean.getCreateTime()) {
				logger.info("[CoreService] [openid:{}] [Repeat]", reqBaseMessageBean.getFromUserName());
				return true;
			}
		}
		map.put(reqBaseMessageBean.getFromUserName(), reqBaseMessageBean);
		return false;
	}
    
    /**
     * 获取返回给WeChat服务号的信息
     * @date 2015-8-8
     */
    private RespBaseMessageBean toResponse(
    		String type, ReqBaseMessageBean reqBaseMessageBean) throws Exception {
    	RespBaseMessageBean respBaseMessageBean = null;
    	IMessageService messageService = null;
    	messageService = ServiceFactory.getInstance().createService(type);
    	
    	if(null != messageService){
    		respBaseMessageBean = messageService.handleResponse(reqBaseMessageBean);
    	}
    	
    	return respBaseMessageBean;
    }

	/**
     * 处理事件信息
     * @date 2015-8-7
     */
    private RespBaseMessageBean handleEvent(
    		Map<String, String> requestMap,ReqBaseMessageBean reqBaseMessageBean) throws Exception {
    	RespBaseMessageBean respBaseMessageBean = null;
    	// 事件类型  
        String eventType = requestMap.get("Event");  
        // 订阅  
        if (WechatConstant.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {  
        	respBaseMessageBean = handleSubscribeEvent(requestMap, reqBaseMessageBean);
        }  
        // 取消订阅
        else if (WechatConstant.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {  
        	respBaseMessageBean = handleUnSubscribe(requestMap, reqBaseMessageBean);
        }  
        // 已关注时的事件操作
        else if(WechatConstant.EVENT_TYPE_SCAN.equals(eventType)){
        	respBaseMessageBean = handleScan(requestMap, reqBaseMessageBean);
        }
        // 自定义菜单点击事件  
        else if (WechatConstant.EVENT_TYPE_CLICK.equals(eventType)) {  
        	respBaseMessageBean = handleClickEvent(requestMap, reqBaseMessageBean);
        } 
        //获取用户地理位置
        else if(WechatConstant.EVENT_TYPE_LOCATION.equals(eventType)){
        	respBaseMessageBean = handleLocationEvent(requestMap, reqBaseMessageBean);
        }
		return respBaseMessageBean;
	}

    /**
     * 处理获取用户地理位置信息事件
     * @date 2015-9-1
     */
	private RespBaseMessageBean handleLocationEvent(
			Map<String, String> requestMap, ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		// 事件类型  
        String eventType = requestMap.get("Event");  
        ReqEventLocationMessageBean reqEventLocationMessageBean = new ReqEventLocationMessageBean(reqBaseMessageBean);
        reqEventLocationMessageBean.setLatitude(requestMap.get("Latitude"));
        reqEventLocationMessageBean.setLongitude(requestMap.get("Longitude"));
        reqEventLocationMessageBean.setPrecision(requestMap.get("Precision"));
        respBaseMessageBean = toResponse(eventType, reqEventLocationMessageBean);
		return respBaseMessageBean;
	}
	/**
     * 处理自定义菜单点击事件
     */
    private RespBaseMessageBean handleClickEvent(
    		Map<String, String> requestMap,ReqBaseMessageBean reqBaseMessageBean) throws Exception {
    	RespBaseMessageBean respBaseMessageBean = null;
    	String eventKey = requestMap.get("EventKey");
    	
    	respBaseMessageBean = toResponse(eventKey, reqBaseMessageBean);
		return respBaseMessageBean;
	}

	/**
     * 处理订阅公众号事件
	 * @throws HealthExc
     */
	private RespBaseMessageBean handleSubscribeEvent(
			Map<String, String> requestMap,ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		
		String eventType = requestMap.get("Event");  
		ReqEventMessageBean reqEventMessageBean = new ReqEventMessageBean(reqBaseMessageBean);
    	reqEventMessageBean.setEvent(eventType);
    	reqEventMessageBean.setEventKey(requestMap.get("EventKey"));
    	
    	respBaseMessageBean = toResponse(eventType, reqEventMessageBean);
		return respBaseMessageBean;
	}
	
	/**
	 * 处理取消关注公众号事件
	 * @date 2015-8-8
	 */
	private RespBaseMessageBean handleUnSubscribe(
			Map<String, String> requestMap, ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		String eventType = requestMap.get("Event");
		
		// 取消订阅后用户再收不到公众号发送的消息，并删除微信用户与设备的绑定关系
    	respBaseMessageBean = toResponse(eventType, reqBaseMessageBean);
		return respBaseMessageBean;
	}
	
	/**
	 * 处理用户已关注时的事件推送
	 * @date 2015-8-9
	 */
	private RespBaseMessageBean handleScan(
			Map<String, String> requestMap, ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		
		String eventType = requestMap.get("Event");  
		ReqEventMessageBean reqEventMessageBean = new ReqEventMessageBean(reqBaseMessageBean);
    	reqEventMessageBean.setEvent(eventType);
    	reqEventMessageBean.setEventKey(requestMap.get("EventKey"));
    	logger.info("[wechat] [ReqEventMessageBean:{}]", reqEventMessageBean);
    	
    	respBaseMessageBean = toResponse(eventType, reqEventMessageBean);
		return respBaseMessageBean;
	}

	/**
     * 处理文本信息
     * @date 2015-8-7
     */
	private RespBaseMessageBean handleText(
			Map<String, String> requestMap, ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		
		String type = requestMap.get("MsgType");
		ReqTextMessageBean reqTextMessageBean = new ReqTextMessageBean(reqBaseMessageBean);
    	reqTextMessageBean.setContent(requestMap.get("Content"));
    	
    	respBaseMessageBean = toResponse(type, reqTextMessageBean);
		return respBaseMessageBean;
	}
	
	/**
	 * 处理图片信息
	 * @date 2015-8-7
	 */
	private RespBaseMessageBean handleImage(
			Map<String, String> requestMap,ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		
		String type = requestMap.get("MsgType");
		respBaseMessageBean = handleImage(requestMap,reqBaseMessageBean);
        ReqImageMessageBean reqImageMessageBean = new ReqImageMessageBean(reqBaseMessageBean);
        reqImageMessageBean.setPicUrl(requestMap.get("PicUrl"));
        
        respBaseMessageBean = toResponse(type, reqImageMessageBean);
		return respBaseMessageBean;
	}
	
	/**
	 * 处理地理信息
	 * @date 2015-8-7
	 */
	private RespBaseMessageBean handleLocation(
			Map<String, String> requestMap,ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		
		String type = requestMap.get("MsgType");
		ReqLocationMessageBean reqLocationMessageBean = new ReqLocationMessageBean(reqBaseMessageBean);
        reqLocationMessageBean.setLocation_X(requestMap.get("Location_X"));
        reqLocationMessageBean.setLocation_Y(requestMap.get("Location_Y"));
        reqLocationMessageBean.setScale(requestMap.get("Scale"));
        reqLocationMessageBean.setLabel(requestMap.get("Label"));
        
        respBaseMessageBean = toResponse(type, reqLocationMessageBean);
		return respBaseMessageBean;
	}
	
	/**
	 * 处理链接信息
	 * @date 2015-8-7
	 */
	private RespBaseMessageBean handleLink(
			Map<String, String> requestMap,ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		
		String type = requestMap.get("MsgType");
		ReqLinkMessageBean reqLinkMessageBean = new ReqLinkMessageBean(reqBaseMessageBean);
        reqLinkMessageBean.setTitle(requestMap.get("Title"));
        reqLinkMessageBean.setUrl(requestMap.get("Url"));
        reqLinkMessageBean.setDescription(requestMap.get("Description"));
        
        respBaseMessageBean = toResponse(type, reqLinkMessageBean);
		return respBaseMessageBean;
	}
	
	/**
	 * 处理音频信息
	 * @date 2015-8-7
	 */
	private RespBaseMessageBean handleVoice(
			Map<String, String> requestMap,ReqBaseMessageBean reqBaseMessageBean) throws Exception {
		RespBaseMessageBean respBaseMessageBean = null;
		
		String type = requestMap.get("MsgType");
		ReqVoiceMessageBean reqVoiceMessageBean = new ReqVoiceMessageBean(reqBaseMessageBean);
        reqVoiceMessageBean.setMediaId(requestMap.get("MediaId"));
        reqVoiceMessageBean.setFormat(requestMap.get("Format"));
        
        respBaseMessageBean = toResponse(type, reqVoiceMessageBean);
		return respBaseMessageBean;
	}
}
