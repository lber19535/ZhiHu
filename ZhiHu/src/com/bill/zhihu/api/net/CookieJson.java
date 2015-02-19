package com.bill.zhihu.api.net;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

/**
 * 将cookie持久化的时候转为json存在本地，读取的时候再从本地读出来
 * 
 * @author Bill Lv
 *
 */

@JsonObject
public class CookieJson {
	@JsonField
	private String name;
	@JsonField
	private String comment;
	@JsonField(name = "comment_url")
	private String commentUrl;
	@JsonField
	private int version;
	@JsonField
	private String domain;
	@JsonField(name = "expiry_date")
	private long expiryDate;
	@JsonField
	private String path;
	@JsonField
	private String value;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentUrl() {
		return commentUrl;
	}

	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
