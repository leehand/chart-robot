package com.mongcent.risk.manager.entity.vo;

import java.util.List;


public class PageBean<T> {
	private int size = 10; // 每页要显示的记录条数 指定默认为10
	private int page = 1; // 当前页号
	private int total; // 记录总条数
	private List<T> list; // 要显示到页面的数据集合
	
	
	public int getSize() {
		if(size>1000) {throw new RuntimeException("分页size过大...");}
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}

	
	
	
	
}