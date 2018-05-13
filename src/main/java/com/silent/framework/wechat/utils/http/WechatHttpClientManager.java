package com.silent.framework.wechat.utils.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WechatHttpClientManager {
	private Logger log = LoggerFactory.getLogger(WechatHttpClientManager.class);
	private static WechatHttpClientManager instance;
	private PoolingHttpClientConnectionManager httpClientManager = null;
	private RequestConfig requestConfig = null;
	private CloseableHttpClient httpClient = null;
	
	private WechatHttpClientManager(){
		int timeout = 5000;
		httpClientManager = new PoolingHttpClientConnectionManager();
		httpClientManager.setMaxTotal(1024);
		httpClientManager.setDefaultMaxPerRoute(1024);
		requestConfig = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
		httpClient = HttpClients.custom().setConnectionManager(httpClientManager).setDefaultRequestConfig(requestConfig).build();
	}
	
	public static synchronized WechatHttpClientManager getInstance(){
		if(instance==null){
			instance = new WechatHttpClientManager();
		}
		return instance;
	}
	
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
	
	public void printStatus(){
		PoolStats status = httpClientManager.getTotalStats();
		log.info("[AVAILABLE:{}] [LEASED:{}] [MAX:{}] [PENDING:{}]", new Object[]{status.getAvailable(), status.getLeased(), status.getMax(), status.getPending()});
	}
}
