package com.silent.framework.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.silent.framework.wechat.service.CoreService;
import com.silent.framework.wechat.utils.wechat.SignUtil;


/**
 * 核心请求处理类
 * @author TanJin
 * @date 2015-8-6
 */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");  	// 微信加密签名  
        String timestamp = request.getParameter("timestamp");  	// 时间戳  
        String nonce = request.getParameter("nonce");  			// 随机数  
        String echostr = request.getParameter("echostr");  		// 随机字符串  
        
        logger.info("[wechat] [CoreServlet] [signature:{}] [timestamp:{}] [nonce:{}] [echostr:{}]", signature, timestamp, nonce, echostr);
        
        PrintWriter out = response.getWriter();  
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
            out.print(echostr);  
        }  
        out.close();  
        out = null;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
  
        //调用核心业务类接收消息、处理消息  
        String respMessage = CoreService.getInstance().processRequest(request);
          
        //响应消息
        PrintWriter out = response.getWriter();  
        out.print(respMessage);  
        out.close();  
	}
}
