package com.yqy.myresume.bean;

import java.io.Serializable;

/**
 * 
 * Description:首页九宫格功能列表
 */
public class Function implements Serializable{
	private String id;//
	public Function(String id, String iconresource, String title, String sort,
			String flag) {
		super();
		this.id = id;
		this.iconresource = iconresource;
		this.title = title;
		this.sort = sort;
		this.flag = flag;
	}
	private String iconresource;//图片资源
	private String title;//标题
	private String sort;//顺序（数字）升序
	private String flag;//开关 true false
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIconresource() {
		return iconresource;
	}
	public void setIconresource(String iconresource) {
		this.iconresource = iconresource;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
