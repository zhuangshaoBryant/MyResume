package com.yqy.myresume.bean;

import java.io.Serializable;

/**
 * 
 * Description:教育经历
 */
public class EducationExperience implements Serializable{
	private String name;//学校名称
	private String start;//入学时间
	private String end;//毕业时间
	private String education;//学历
	private String professional;//专业
	private String order;//排名
	private String english;//外语水平
	private String course;//所修课程

	public String getScholarship() {
		return scholarship;
	}

	public void setScholarship(String scholarship) {
		this.scholarship = scholarship;
	}

	private String scholarship;//奖学金

	public EducationExperience(String name, String start,
	                           String end, String education, String professional, String order,
	                           String course, String english, String scholarship) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.education = education;
		this.professional = professional;
		this.order = order;
		this.course = course;
		this.english = english;
		this.scholarship =  scholarship;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}



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
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	
}
