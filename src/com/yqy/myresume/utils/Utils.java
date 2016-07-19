package com.yqy.myresume.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

/**
 * COPYRIGHT NOTICE Copyright (c) 2009～2012, IGRSlab All rights reserved.
 *
 * @author wjf
 * @file Utils.java
 * @brief TODO
 * @date 2012-5-10
 */
public class Utils {

	public static final String NICKNAME = "name";

	/**
	 * 通过生日获取年龄
	 *
	 * @param birthday
	 * @return
	 */
	public static final String getAgeByBirthday(String birthday) {
		try {
			if (TextUtils.isEmpty(birthday))
				return "0";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date birthdayDate = sdf.parse(birthday);
			String currTimeStr = sdf.format(new Date());
			Date currDate = sdf.parse(currTimeStr);
			//判断，
			if (birthdayDate.getTime() > currDate.getTime()) {
				return "0";
			}
			long age = (currDate.getTime() - birthdayDate.getTime())
					/ (24 * 60 * 60 * 1000) + 1;
			String year = new DecimalFormat("0.00").format(age / 365f);
			if (TextUtils.isEmpty(year))
				return "0";
			return String.valueOf(new Double(year).intValue());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}

	public static String getSDPath() {
		boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (hasSDCard) {
			return Environment.getExternalStorageDirectory().toString() + "/saving_picture";
		} else
			return Contacts.FACEPATH;
	}

	/**
	 * 通过生日的月份跟日得到星座 YY-MM-DD
	 *
	 * @return
	 */
	public static String getStarByBirth(String birthday) {
		String str = "";
		try {
			String[] strs = birthday.split("-");
			String yue = strs[1];
			String ri = strs[2];
			int yueint = Integer.parseInt(yue);
			int riint = Integer.parseInt(ri);
			str = "";
			switch (yueint) {
				case 1:
					if (riint <= 19)
						str = "摩羯座";
					else
						str = "水瓶座";
					break;
				case 2:
					if (riint <= 18)
						str = "水瓶座";
					else
						str = "双鱼座";
					break;
				case 3:
					if (riint <= 20)
						str = "双鱼座";
					else
						str = "白羊座";
					break;
				case 4:
					if (riint <= 19)
						str = "白羊座";
					else
						str = "金牛座";
					break;
				case 5:
					if (riint <= 20)
						str = "金牛座";
					else
						str = "双子座";
					break;
				case 6:
					if (riint <= 21)
						str = "双子座";
					else
						str = "巨蟹座";
					break;
				case 7:
					if (riint <= 22)
						str = "巨蟹座";
					else
						str = "狮子座";
					break;
				case 8:
					if (riint <= 22)
						str = "狮子座";
					else
						str = "处女座";
					break;
				case 9:
					if (riint <= 22)
						str = "处女座";
					else
						str = "天秤座";
					break;
				case 10:
					if (riint <= 23)
						str = "天秤座";
					else
						str = "天蝎座";
					break;
				case 11:
					if (riint <= 21)
						str = "天蝎座";
					else
						str = "射手座";
					break;
				case 12:
					if (riint <= 21)
						str = "射手座";
					else
						str = "摩羯座";
					break;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 把日期转为字符串 yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	/**
	 * 把字符串转为日期 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Date stringToDate(String strDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(strDate);
	}

	/**
	 * 根据用户生日计算年龄
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

	/**
	 * 获取当前日期的格式 格式：2012-05-10
	 *
	 * @return
	 */
	public static String getCurrentDateYMD() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 获取从今天开始，往后七天的日期数组
	 *
	 * @return
	 */
	public static String[] getFromDateToWeek() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String[] arr = new String[7];
		for (int i = 0; i < arr.length; i++) {
			Date date = new Date();
			long myTime = (date.getTime() / 1000) + 60 * 60 * 24 * i;
			date.setTime(myTime * 1000);
			arr[i] = simpleDateFormat.format(date);
		}
		return arr;
	}

	/**
	 * 获取当前日期的格式 格式：2012-05-10 09:04:55
	 *
	 * @return
	 */
	public static String getCurrentDateYMDHMS() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}

	public static Date dateStrngFormatToDate(String date) {
		SimpleDateFormat sdf_yyyy_mm_dd_HH_mm_ss = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			return sdf_yyyy_mm_dd_HH_mm_ss.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// public static Bitmap getSmallBitmapFromFront(Bitmap mp){
	// Bitmap bit = null;
	// if(mp != null)bit = Bitmap.createBitmap(mp,0, 0, 20, mp.getHeight());
	// return bit;
	// }

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @param fontScale （DisplayMetrics类中属性scaledDensity）
	 * @return
	 * @author jinsc
	 */
	public static int sp2px(float spValue, Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (spValue * scale + 0.5f);
	}

	/**
	 * 根据值剪切字符串
	 *
	 * @param org   原始字符串
	 * @param split 剪切字符串
	 */
	public static String spliteStringByString(String org, String split) {
		if (null == org || "".equals(org))
			return "";
		final int startIdext = org.indexOf(split);
		if (startIdext < 0)
			return "";
		final String tempStr = org.substring(startIdext);
		final int endIndex = tempStr.indexOf("&");
		if (endIndex < 0) {
			return tempStr.substring(split.length() + 1);
		}
		return tempStr.substring(split.length() + 1, endIndex);
	}

	/**
	 * 根据值剪切字符串
	 *
	 * @param json
	 * @param key  json中的键值
	 */
	public static String getStringFromJson(String json, String key) {
		try {
			JSONObject js = new JSONObject(json);
			return js.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return "";
		}
	}

	/**
	 * 从本地获取相片路径.
	 *
	 * @param path String
	 * @return Bitmap
	 */
	public static Bitmap getImageFromSdcard(String path) {
		// FileInputStream fis;
		final File file = new File(path);
		if (file.exists()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			// 此时返回bm为空
			Bitmap bitmap = BitmapFactory.decodeFile(path, options);
			int width = 640;
			options.inJustDecodeBounds = false;
			int be = (options.outWidth / width);
			if (be <= 0) {
				be = 1;
			}
			options.inSampleSize = be;
			// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为false
			bitmap = BitmapFactory.decodeFile(path, options);
			// fis = new FileInputStream(path);
			// final Bitmap bm = BitmapFactory.decodeStream(fis);
			// fis.close();
			return bitmap;
		} else {
			return null;
		}
	}

	public static Bitmap imageZoom(Bitmap bitMap) {
		// int width = 640;
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		// final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		// final byte[] b = baos.toByteArray();
		// 获取bitmap大小 是允许最大大小的多少�?
		// float scale = ((float)(bitMap.getHeight()) /
		// ((float)bitMap.getWidth()));
		// double height = 0;
		// if (scale > 1) {
		// height = width;
		// width = (int)(height / scale);
		// } else {
		// height = (int)(width * scale);
		// }
		// 开始压�?此处用到平方�?将宽带和高度压缩掉对应的平方根�?
		// �?.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小�?
		bitMap = zoomImage(bitMap, 640, 480);
		return bitMap;
	}

	/***
	 * 图片的缩放方法
	 *
	 * @param bgimage   ：源图片资源
	 * @param newWidth  ：缩放后宽度
	 * @param newHeight ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
	                               double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	/**
	 * 全角半角转换
	 *
	 * @param input
	 * @return
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static final Date getStringToDate(String dateStr, String formatStr) {
		final DateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 根据图片路径，获得图片字节数组.
	 *
	 * @param filePath
	 * @return
	 */
	public static byte[] getPictureBytes(String filePath) {
		return getBitmapByte(getImageFromSdcard(filePath));
	}

	/**
	 * bitmap转化为二进制流
	 *
	 * @param bitmap Bitmap
	 * @return byte byte[]
	 */
	public static byte[] getBitmapByte(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 65, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * . 设置*号屏蔽显示
	 *
	 * @param str    String
	 * @param head   int
	 * @param behind int
	 * @return String
	 */
	public static String setHideStarDisplay(String str, int head, int behind) {
		if (str != null) {
			if (str.length() <= (head + behind)) {
				return str;
			} else {
				final int length = str.length();
				final StringBuffer strBuffer = new StringBuffer();
				strBuffer.append(str.substring(0, head));
				for (int i = 0; i < length - head - behind; i++) {
					strBuffer.append("*");
				}
				strBuffer.append(str.substring((length - behind), length));
				return strBuffer.toString();
			}
		}
		return str;
	}

	/**
	 * . 设置字符串每四个字一个空格。
	 *
	 * @param str String
	 * @return String
	 */
	public static String formateStringBySpace(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if ((i + 1) % 4 == 0) {
				sb.append(str.charAt(i));
				sb.append(" ");
			} else {
				sb.append(str.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 获得字符串中整数部分
	 *
	 * @param str
	 * @return
	 */
	public static String getInteger(String str) {
		if ("".equals(str)) {
			return "00.";
		}
		String[] strs = str.split("\\.");
		if (strs.length == 0) {
			return str + ".";
		}
		return strs[0] + ".";
	}

	/**
	 * 获得字符串中小数部分
	 *
	 * @param str
	 * @return
	 */
	public static String getDecimal(String str) {
		if ("".equals(str)) {
			return "00";
		}
		String[] strs = str.split("\\.");
		if (strs.length == 2) {
			return strs[1];
		} else {
			return "00";
		}

	}

	/**
	 * 字符串是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getUrlEncode(String str) {
		String str_result = "";
		try {
			str_result = java.net.URLEncoder.encode(str, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str_result;
	}

	/**
	 * 获取前N天、前N月的日期
	 *
	 * @param date   当前日期
	 * @param day    获取的类型（如获取月份：Calendar.MONTH）
	 * @param before 获取之前多久的日期或月份（如获取前一天:-1 获取前两天：-2）
	 * @return Date 返回Date类型
	 */
	public static Date getBeforeDate(Date date, int day, int before) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(day, before);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 说明：把给定字符串string转化成相同长度的******字符串
	 *
	 * @param string 给定需要转化的字符串
	 * @return
	 */
	public static String getXXX(String string) {
		StringBuffer sb = new StringBuffer();
		try {
			if (!TextUtils.isEmpty(string)) {
				int length = string.length();
				for (int i = 0; i < length; i++) {
					sb.append("*");
				}
			}
		} catch (Exception e) {
		}
		return sb.toString();
	}

	/**
	 * 说明：去除字符串中所有的空格字符
	 *
	 * @param string 待有空格的字符串
	 * @return 返回没有空格的字符串
	 */
	public static String trim(String string) {
		return TextUtils.isEmpty(string) ? "" : string.replaceAll(" ", "");
	}

	/**
	 * @Title: GetImg16Str
	 * @author: jinsc
	 * @Description: 将图片转换为16进制
	 * @since: 2015-5-5 下午6:55:16
	 */
	public static String GetImg16Str(String filePath) {
		byte[] result = null;
		try {
			if (!new File(filePath).exists()) {
				return "";
			}
			FileInputStream fis = new FileInputStream(filePath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = fis.read(buff)) != -1) {
				bos.write(buff, 0, len);
			}
			result = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byte2HexStr(result);
	}

	private static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int i = 0; i < b.length; i++) {
			stmp = Integer.toHexString(b[i] & 0Xff);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * @param src    16进制字符
	 * @param output 输出图片保存的地址
	 * @Title: saveToImgFile
	 * @author: jinsc
	 * @Description: 16进制还原成图片
	 * @since: 2015-5-7 下午4:08:15
	 */
	public static void saveToImgFile(String src, String output) {
		if (src == null || src.length() == 0) {
			return;
		}
		try {
			FileOutputStream out = new FileOutputStream(new File(output));
			byte[] bytes = src.getBytes();
			for (int i = 0; i < bytes.length; i += 2) {
				out.write(charToInt(bytes[i]) * 16 + charToInt(bytes[i + 1]));
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int charToInt(byte ch) {
		int val = 0;
		if (ch >= 0x30 && ch <= 0x39) {
			val = ch - 0x30;
		} else if (ch >= 0x41 && ch <= 0x46) {
			val = ch - 0x41 + 10;
		}
		return val;
	}

	/**
	 * Json 转成 Map<>
	 *
	 * @param jsonStr
	 * @return
	 * @author jinsc
	 */
	public static Map<String, Object> getMapForJson(String jsonStr) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonStr);

			Iterator<String> keyIter = jsonObject.keys();
			String key;
			Object value;
			Map<String, Object> valueMap = new HashMap<String, Object>();
			while (keyIter.hasNext()) {
				key = keyIter.next();
				value = jsonObject.get(key);
				valueMap.put(key, value);
			}
			return valueMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Json 转成 List<Map<>>
	 *
	 * @param jsonStr
	 * @return
	 * @author jinsc
	 */
	public static List<Map<String, Object>> getlistForJson(String jsonStr) {
		List<Map<String, Object>> list = null;
		try {
			JSONArray jsonArray = new JSONArray(jsonStr);
			JSONObject jsonObj;
			list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObj = (JSONObject) jsonArray.get(i);
				list.add(getMapForJson(jsonObj.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
