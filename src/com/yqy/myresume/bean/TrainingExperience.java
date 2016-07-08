package com.yqy.myresume.bean;

import java.io.Serializable;

/**
 * 
 * Description:培训经历
 */
public class TrainingExperience implements Serializable{
	private String name;//机构名称
	private String start;//开始时间
	private String end;//结束时间
	private String content;//培训内容
	private String desc;//培训描述  
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
