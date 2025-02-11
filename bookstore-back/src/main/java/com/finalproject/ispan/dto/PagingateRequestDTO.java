package com.finalproject.ispan.dto;

public class PagingateRequestDTO { //改名成統一版本，適用於優惠券和訂單分頁功能
	private int page = 0;
    private int size = 10;

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
}
