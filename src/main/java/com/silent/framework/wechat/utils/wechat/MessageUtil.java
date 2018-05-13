package com.silent.framework.wechat.utils.wechat;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.silent.framework.wechat.bean.response.Article;
import com.silent.framework.wechat.bean.response.RespMusicMessageBean;
import com.silent.framework.wechat.bean.response.RespNewsMessageBean;
import com.silent.framework.wechat.bean.response.RespTextMessageBean;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息处理工具类
 * @date 2015-7-21
 */
public class MessageUtil {
	
    /**
     * XML 转换为 object
	 * 解析微信发来的请求（XML） 
	 * @date 2015-7-21
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
	    // 将解析结果存储在HashMap中  
	    Map<String, String> map = new HashMap<String, String>();  
	  
	    // 从request中取得输入流  
	    InputStream inputStream = request.getInputStream();  
	    // 读取输入流  
	    SAXReader reader = new SAXReader();  
	    Document document = reader.read(inputStream);  
	    // 得到XML根元素  
	    Element root = document.getRootElement();  
	    // 得到根元素的所有子节点  
	    List<Element> elementList = root.elements();  
	  
	    // 遍历所有子节点  
	    for (Element e : elementList) {
	    	map.put(e.getName(), e.getText());  
	    } 
	  
	    // 释放资源  
	    inputStream.close();  
	    inputStream = null;  
	    return map;  
	} 
	
	/** 
	 * 文本消息对象转换成XML 
	 *  
	 * @param textMessageBean 文本消息对象 
	 * @return XML 
	 */  
	public static String textMessageToXml(RespTextMessageBean textMessageBean) {  
	    xstream.alias("xml", textMessageBean.getClass());  
	    return xstream.toXML(textMessageBean);  
	}
	
	/** 
	 * 音乐消息对象转换成XML 
	 *  
	 * @param musicMessageBean 音乐消息对象 
	 * @return XML 
	 */  
	public static String musicMessageToXml(RespMusicMessageBean musicMessageBean) {  
	    xstream.alias("xml", musicMessageBean.getClass());  
	    return xstream.toXML(musicMessageBean);  
	}
	
	/** 
	 * 图文消息对象转换成XML 
	 *  
	 * @param newsMessageBean 图文消息对象 
	 * @return XML 
	 */  
	public static String newsMessageToXml(RespNewsMessageBean newsMessageBean) {  
	    xstream.alias("xml", newsMessageBean.getClass());  
	    xstream.alias("item", new Article().getClass());  
	    return xstream.toXML(newsMessageBean);  
	}
	
	/**
	 * 扩展XStream，使其支持CDATA块
	 */
	private static XStream xstream = new XStream(new XppDriver() {  
	    public HierarchicalStreamWriter createWriter(Writer out) {
	        return new PrettyPrintWriter(out) {  
	            // 对所有XML节点的转换都增加CDATA标记  
	            boolean cdata = true;  
	  
	            @SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {  
	                super.startNode(name, clazz);  
	            }  
	            
	            protected void writeText(QuickWriter writer, String text) {  
	                if (cdata) {  
	                    writer.write("<![CDATA[");  
	                    writer.write(text);  
	                    writer.write("]]>");  
	                } else {  
	                    writer.write(text);  
	                }  
	            }  
	        };  
	    }  
	});
}
