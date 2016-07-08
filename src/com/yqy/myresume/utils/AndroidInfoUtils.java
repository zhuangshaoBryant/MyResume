package com.yqy.myresume.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Description:获取手机信息
 *
 */
public class AndroidInfoUtils {

	/**
	 * 获取IMEI码
	 * @param context
	 * @return
	 */
	public static String getImeiCode(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String res = tm.getDeviceId();
		if (res == null || "".equals(res)) {
			return "";
		}
		return res;
	}

	/**
	 * 获取IMSI码
	 *
	 * @param context
	 * @return
	 */
	public static String getImsiCode(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String res = tm.getSubscriberId();
		if (res == null || "".equals(res)) {
			return "";
		}
		return res;
	}

	public static String getTerminalCode(Context context) {
		String terminal = "";
		try {
			terminal = MD5.getMd5(getImeiCode(context) + getImsiCode(context)
					+ getAndroidId(context));
			if (!TextUtils.isEmpty(terminal)) {
				terminal = terminal.toUpperCase();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (TextUtils.isEmpty(terminal))
			return terminal;
		return terminal.substring(terminal.length() - 10, terminal.length());
	}

	public static String getTerminalCodeCommon(Context context) {
		String terminal = "";
		try {
			terminal = MD5.getMd5(getImeiCode(context) + getImsiCode(context)
					+ getAndroidId(context));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (TextUtils.isEmpty(terminal))
			return terminal;
		return terminal.substring(terminal.length() - 10, terminal.length());
	}

	/**
	 * 获取AndroidId
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context) {
		String androidId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return androidId;
	}

	/**
	 * 获取系统版本
	 *
	 * @return
	 */
	public static String getOs() {
		String os = Build.VERSION.RELEASE;
		return os;
	}

	/**
	 * 获取本地mac地址
	 *
	 * @param context
	 * @return
	 */
	public static String getLocalMacAddress(Context context) {
		final WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		final WifiInfo info = wifiManager.getConnectionInfo();
		String res = "";
		if (info != null)
			res = info.getMacAddress();
		if (res == null || "".equals(res)) {
			return "00:00:00:00:00:00";
		}
		return res;
	}

	/**
	 * 判断是否连接互联网
	 * @param context
	 * @return
	 */
	public static boolean isNetConnected(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo network = connectivityManager.getActiveNetworkInfo();
			if (connectivityManager != null) {
				if (network != null && network.isConnected()) {
					if (network.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 当前应用的versionName
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		String version = "";
		try {
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	
	/**
	 * 获取手机尺寸
	 * @param context
	 * @return
	 */
	public static String getDisplay(Context context) {

        final DisplayMetrics metric = new DisplayMetrics();
        final WindowManager manager = (WindowManager) context
                .getSystemService("window");
        manager.getDefaultDisplay().getMetrics(metric);
        final int width = metric.widthPixels;
        final int height = metric.heightPixels;
        final int densityDpi = metric.densityDpi;
        // 对角线的长度
        final Double diagonal = Math.sqrt(width * width + height * height);
        final Double d = diagonal / densityDpi;
        String c = Double.toString(d);
        if (c != null && c.indexOf(".") != -1) {
            c = c.substring(0, c.indexOf(".") + 2);
        }
        return c;
    }
	
	/**
	 * 获取手机分辨率
	 * @param context
	 * @return
	 */
	public static String getResolution(Context context){
    	final DisplayMetrics metric = new DisplayMetrics();
        final WindowManager manager = (WindowManager) context
                .getSystemService("window");
        manager.getDefaultDisplay().getMetrics(metric);
        final int width = metric.widthPixels;
        final int height = metric.heightPixels;
        return width+"X"+height;
    }
	
	/**
	 * 获取本机号码
	 * @param context
	 * @return
	 */
	public static String getTelephoneNum(Context context) {
        final TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        final String res = tm.getLine1Number();
        if (res == null || "".equals(res)) {
            return "";
        }
        return res;
    }
	
	/**
	 * 获取手机型号
	 * @return
	 */
	public static String getModel() {
		String model = Build.MODEL;
		return model;
	}
	
	/**
	 * 判断应用是否开启debug
	 * @param context
	 * @return
	 */
	public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
                return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {
            
        }
        return false;
    }
	
	/**
	 * 获取android sdk 版本号
	 * @return
	 */
	public static int getAndroidSDKVersion() {
		int version = 0;
		try{
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		}catch(Exception e){
			
		}
		return version;
	}

	/**
	 * SHA1加密签名
	 * @param s
	 * @return
	 */
	public static String SHA1(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
	public static String toHexString(byte[] keyData) {
        if (keyData == null) {
            return null;
        }
        int expectedStringLen = keyData.length * 2;
        StringBuilder sb = new StringBuilder(expectedStringLen);
        for (int i = 0; i < keyData.length; i++) {
            String hexStr = Integer.toString(keyData[i] & 0x00FF,16);
            if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }
	
}
