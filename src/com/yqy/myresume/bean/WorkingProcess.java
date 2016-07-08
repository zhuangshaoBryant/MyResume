package com.yqy.myresume.bean;

import java.io.Serializable;

/**
 * 
 * Description:工作历程
 */
public class WorkingProcess implements Serializable{
	private String name;//公司名称
	private String start;//入职时间
	private String end;//离职时间
	private String title;//职位名称
	private String money;//职位薪资
	private String desc;//工作描述
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
