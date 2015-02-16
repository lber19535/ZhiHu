package com.bill.zhihu.api.utils;

import com.bill.jeson.annotation.JsonField;
import com.bill.jeson.annotation.JsonObject;

@JsonObject
public class CookieJson {
	@JsonField
	private String comment;
	@JsonField(name = "comment_url")
	private String commentUrl;
	@JsonField
	private String version;
	@JsonField
	private String domain;
	@JsonField(name = "expiry_date")
	private long expiryDate;
	@JsonField
	private String path;
	@JsonField
	private String value;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
