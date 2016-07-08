package com.yqy.myresume.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.os.Environment;

/**
 * Description: File 辅助类
 *
 */
public class FileUtils {
	private String downDir = "/down";
	private String appsDir = "/apps";
	private String layoutPhoneDir = "/layout/phone";
	private String layoutPadDir = "/layout/pad";
	private String layoutDir = "/layout";
	private String scriptDir = "/script";
	private String resDir = "/res";
	private String resImgDir = "/res/images";
	private String resArrayDataDir = "/res/data/array";
	private String resStringDataDir = "/res/data/string";
	private String resDimensDataDir = "/res/data/dimens";
	private String manifest = "manifest";
	private String filesDir;
	private String cacheDir;
	private String dbDir;
	private String appDbString;
	public String xmlSuffix = ".xml";
	private String apkSuffix = ".apk";
	public String sdcardCache = "yqy/cache";
	private Set<String> filterApp;
	private final String macx = "__macosx";
	private final String publicRes = "public";
	private final String publicCommon = "common";
	private final String publicDS = ".ds_store";
	private final String publicBottomString = "bottom";
	private final String publicLoginString = "login";
	private final String apkName = "yqy.apk";
	private final String logName = "log.log";
	private final String externalDirString = Environment
			.getExternalStorageDirectory().toString() + "/yqy";
	private final String externalDirApk = externalDirString + "/apk";
	private final String externalDirLog = externalDirString + "/log";

	private static FileUtils instance = null;

	public static FileUtils getInstance() {
		synchronized (FileUtils.class) {
			if (instance == null)
				instance = new FileUtils();
		}
		return instance;
	}

	public String __macx() {
		return macx;
	}

	public String getLoginViewString() {
		return publicLoginString;
	}

	public String getApkSuffix() {
		return apkSuffix;
	}

	public String geCommonDir() {
		return getAppsDir() + File.separator + publicCommon;
	}

	public String getFilesDir() {
		return filesDir;
	}

	public String getCacheDir() {
		return cacheDir;
	}

	public String getDBDir() {
		return dbDir;
	}

	public String getAppDBDir() {
		return appDbString;
	}

	public String getPublicPath() {
		return getAppsDir() + "/" + publicRes + "/";
	}

	public String getPublicScriptPath() {
		return getPublicPath() + "script/";
	}

	public String getLayoutPadDir(String appid) {
		if (appid.equals(publicRes))
			return getAppsDir() + "/" + appid + layoutDir + "/";
		return getAppsDir() + "/" + appid + layoutPadDir + "/";
	}

	public String getDownDir() {
		return filesDir + downDir;
	}

	public String getApkUpdatePathFile() {
		return externalDirApk + "/" + apkName;
	}

	public String getLogPathFile() {
		String fileNamString = Utils.getCurrentDateYMD() + "_" + logName;
		return externalDirLog + "/" + fileNamString;
	}

	public String getLogPathFileInDebugModle() {
		// String fileNamString = Utils.getCurrentDateYMD() + "_" +logName;
		return externalDirLog + "/debug.log";
	}

	public String getApkUpdatePath() {
		return externalDirApk;
	}

	public String getAppsDir() {
		return filesDir + appsDir;
	}

	public String getAppsScriptDir(String appid) {
		return getAppsDir() + "/" + appid + scriptDir + "/";
	}

	public String getAppsResDir(String appid) {
		return getAppsDir() + "/" + appid + resDir + "/";
	}

	public void init(Context context) {
		filterApp = new HashSet<String>();
		filterApp.add(publicRes);
		filterApp.add(publicCommon);
		filterApp.add(macx);
		filterApp.add(publicDS);
		filesDir = context.getFilesDir().getAbsolutePath();
		cacheDir = context.getCacheDir().getAbsolutePath();
		dbDir = "/data/data/" + context.getPackageName() + "/databases";
		appDbString = "/data/data/" + context.getPackageName()
				+ "/app_database";
		String[] fileDirs = new String[] { getDownDir(), getAppsDir(),
				externalDirApk };
		for (String string : fileDirs) {
			File file = new File(string);
			if (!file.exists())
				file.mkdirs();
		}
	}

	public void delete(String path) {
		File file = new File(path);
		System.out.println("path=" + path);
		if (file != null) {
			System.out.println(file.exists());
			System.out.println(file.delete());
		}
		// file.deleteOnExit();
	}

}
