package com.zhihu.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="story")
public class Stories {
	@DatabaseField
	private String images;
	@DatabaseField
	private int type;
	@DatabaseField
	private int id;
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		if(images!=null){
			images.replace("/", "");
		}
		this.images = images;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGa_prefix() {
		return ga_prefix;
	}
	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}
	public Stories() {
		super();
	}
	public Stories(String images, int type, int id, String ga_prefix,
			String title) {
		super();
		this.images = images;
		this.type = type;
		this.id = id;
		this.ga_prefix = ga_prefix;
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private String ga_prefix;
	private String title;
}
