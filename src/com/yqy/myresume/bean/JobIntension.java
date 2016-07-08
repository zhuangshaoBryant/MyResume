package com.yqy.myresume.bean;

import java.io.Serializable;

public class JobIntension implements Serializable{
	private String property;//性质
	private String job;//职业
	private String trade;//行业  
	private String money;//月薪
	private String now;//目前状况 
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}

}
