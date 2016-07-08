package com.yqy.myresume.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 
 * Description:用户信息
 */
public class UserInfo implements Serializable{
	private String name;//姓名
	private String sex;//性别
	private String birth;//生日 YY-MM-DD
	private String workingage;//工作年龄
	private String tel;//手机号码
	private String email;//电子邮箱
	private String hometown;//故乡

	
	public UserInfo(String name, String sex, String birth,
			String workingage, String tel, String email, String hometown) {
		super();
		this.name = name;
		this.sex = sex;
		this.birth = birth;
		this.workingage = workingage;
		this.tel = tel;
		this.email = email;
		this.hometown = hometown;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getWorkingage() {
		return workingage;
	}
	public void setWorkingage(String workingage) {
		this.workingage = workingage;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
}
