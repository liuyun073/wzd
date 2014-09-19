package com.liuyun.site.tool;

public class Page {
	private int total;
	private int currentPage;
	private int pages;
	private int pernum;
	private int start;
	private int end;
	public static int ROWS = 10;

	public Page() {
	}

	public Page(int total, int currentPage) {
		this(total, currentPage, ROWS);
	}

	public Page(int total, int currentPage, int pernum) {
		this.total = total;
		this.currentPage = ((currentPage == 0) ? 1 : currentPage);
		this.pernum = pernum;
		this.pages = (int) Math.ceil((total + 0.0D) / pernum);
		count();
	}

	private void count() {
		this.start = (this.pernum * (this.currentPage - 1));
		this.end = (this.pernum * this.currentPage);
		if (this.end > this.total)
			this.end = this.total;
	}

	public boolean hasPreview() {
		return (this.pages > 1) && (this.currentPage > 1);
	}

	public boolean hasNext() {
		return (this.pages > 1) && (this.currentPage < this.pages);
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPages() {
		return this.pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPernum() {
		return this.pernum;
	}

	public void setPernum(int pernum) {
		this.pernum = pernum;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return this.end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}