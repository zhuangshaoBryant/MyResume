package com.yqy.myresume.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * sharedpreferences工具类
 * 
 */
public class SPUtil {

	private SharedPreferences sp;
	private static SPUtil instance = null;

	public static SPUtil getInstance() {
		synchronized (SPUtil.class) {
			if (instance == null)
				instance = new SPUtil();
		}
		return instance;
	}
	
	public void init(Context context){
		if(sp == null){
			sp = context.getSharedPreferences("myresume", Context.MODE_PRIVATE);
		}
	}

	public void write(String key, String value) {
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void write(String key, int value) {
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public void write(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 */
	public void remove(String key) {
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}

	public int read(String key, int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public boolean read(String key, Boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	public String read(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}
}