package com.silent.framework.wechat.utils.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.silent.framework.wechat.utils.exception.ServiceRuntimeException;

/**
 * HTTP网络请求处理工具类
 * @author TanJin
 * @date 2016-8-14
 */
public class WechatHttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(WechatHttpUtils.class);
	
	/**
	 * POST请求
	 * @param url
	 * @param reqEntity
	 * @param headers
	 * @return byte[]
	 * @date 2016-8-14
	 */
	public static byte[] doPost(String url, HttpEntity reqEntity, Header[] headers) throws IOException {
		CloseableHttpResponse response = null;
		long time = System.currentTimeMillis();
		try {
			logger.debug("post start url:{}", url);
			CloseableHttpClient client = WechatHttpClientManager.getInstance().getHttpClient();
			HttpPost request = new HttpPost(url);
			if(headers!=null) {
				request.setHeaders(headers);
			}
			request.setConfig(RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(10000).build());
			request.setEntity(reqEntity);
			response = client.execute(request, new BasicHttpContext());
			int status = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == status || HttpStatus.SC_PARTIAL_CONTENT==status) {
				return EntityUtils.toByteArray(response.getEntity());
			} else {
				throw new ServiceRuntimeException("receive code:" + status);
			}
		} finally {
			logger.debug("post end url:{} time:{}", url, System.currentTimeMillis()-time);
			if(response!=null) {
				response.close();
			}
		}
	}
	
	public static String doPostString(String url, HttpEntity reqEntity, Header[] headers) throws Exception {
		byte[] result = doPost(url, reqEntity, headers);
		return new String(result);
	}
	
	public static String doPostString(String url, JSON json, Header[] headers) {
		HttpEntity reqEntity = null;
		if (json != null) {
			reqEntity = new ByteArrayEntity(json.toString().getBytes());
		}
		try {
			return WechatHttpUtils.doPostString(url, reqEntity, headers);
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	public static JSONObject doPostToJson(String url, JSONObject json, Header[] headers) throws Exception {
		String result = doPostString(url, json, headers);
		return JSONObject.parseObject(result);
	}
	
	/**
	 * GET请求
	 * @date 2016-8-14
	 */
	public static byte[] doGet(String url, Header[] headers) throws IOException {
		CloseableHttpResponse response = null;
		long time = System.currentTimeMillis();
		try {
			logger.debug("get start url:{}", url);
			CloseableHttpClient client = WechatHttpClientManager.getInstance().getHttpClient();
			HttpGet request = new HttpGet(url);
			
			if(headers!=null) {
				request.setHeaders(headers);
			}
			response = client.execute(request, new BasicHttpContext());
			int status = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == status || HttpStatus.SC_PARTIAL_CONTENT==status) {
				return EntityUtils.toByteArray(response.getEntity());
			} else {
				throw new ServiceRuntimeException("receive code:" + status);
			}
		} finally {
			logger.debug("get end url:{} time:{}", url, System.currentTimeMillis()-time);
			if(response!=null) {
				response.close();
			}
		}
	}
	
	public static String doGetToString(String url, Header[] headers) throws IOException {
		byte[] result = doGet(url, headers);
		return new String(result);
	}
	
	public static JSONObject doGetToJson(String url, Header[] headers) throws Exception {
		String result = doGetToString(url, headers);
		return JSON.parseObject(result);
	}
}
