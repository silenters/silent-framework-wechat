package com.silent.framework.wechat.bean.response;

import java.util.List;

/**
 * 图文消息
 * @date 2015-7-21
 */
public class RespNewsMessageBean extends RespBaseMessageBean {

	// 图文消息个数，限制为10条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<Article> Articles;
    
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	
	@Override
	public String toString() {
		return "RespNewsMessageBean [ArticleCount=" + ArticleCount
				+ ", Articles=" + Articles + ", getToUserName()="
				+ getToUserName() + ", getFromUserName()=" + getFromUserName()
				+ ", getCreateTime()=" + getCreateTime() + ", getMsgType()="
				+ getMsgType() + ", getFuncFlag()=" + getFuncFlag() + "]";
	}
	
}
