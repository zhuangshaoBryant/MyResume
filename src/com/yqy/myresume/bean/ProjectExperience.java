package com.yqy.myresume.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Description:项目经验
 */
public class ProjectExperience implements Serializable{
	private String id;
	private String name;//项目名称
	private String start;//开始时间
	private String end;//结束时间
	private String isonline;//是否上线(true、false)
	private String tool;//开发工具
	private String type;//项目类型
	private String system;//操作系统
	private String brief;//项目简介
	private String desc;//责任描述
	private String lights;//项目亮点
	private List<String> photoShowPath;

	public List<String> getPhotoShowPath() {
		return photoShowPath;
	}

	public void setPhotoShowPath(List<String> photoShowPath) {
		this.photoShowPath = photoShowPath;
	}

	public ProjectExperience(String id, String name, String start, String end,
	                         String isonline, String tool, String type, String system,
	                         String brief, String desc, String lights , List<String> photoShowPath) {
		super();
		this.id = id;
		this.name = name;
		this.start = start;
		this.end = end;
		this.isonline = isonline;
		this.tool = tool;
		this.type = type;
		this.system = system;
		this.brief = brief;
		this.desc = desc;
		this.lights = lights;
		this.photoShowPath = photoShowPath;
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
	public String getIsonline() {
		return isonline;
	}
	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}
	public String getTool() {
		return tool;
	}
	public void setTool(String tool) {
		this.tool = tool;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLights() {
		return lights;
	}
	public void setLights(String lights) {
		this.lights = lights;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
