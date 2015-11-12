package com.zhihu.entity;

import java.util.ArrayList;
import java.util.List;

public class ListItem {
	private List<Stories> list=new ArrayList<Stories>();
	private String date;
	public List<Stories> getList() {
		return list;
	}
	public void setList(List<Stories> list) {
		this.list .addAll(list);
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
