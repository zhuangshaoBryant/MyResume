package com.yqy.myresume.bean;

import java.io.Serializable;

/**
 * 专业技能
 * Description:
 */
public class SpecializedSkill implements Serializable{
	private String name;//专业名称
	private String start;//开始时间
	private String proficiency;//熟练度
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
	public String getProficiency() {
		return proficiency;
	}
	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}

}
