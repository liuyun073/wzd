package com.liuyun.site.payment;


public class Pay {
	
	private TranGood good;
	private String frontMerUrl;
	private String backgroundMerUrl;

	public TranGood getGood() {
		return this.good;
	}

	public void setGood(TranGood good) {
		this.good = good;
	}

	public String getFrontMerUrl() {
		return this.frontMerUrl;
	}

	public void setFrontMerUrl(String frontMerUrl) {
		this.frontMerUrl = frontMerUrl;
	}

	public String getBackgroundMerUrl() {
		return this.backgroundMerUrl;
	}

	public void setBackgroundMerUrl(String backgroundMerUrl) {
		this.backgroundMerUrl = backgroundMerUrl;
	}
}