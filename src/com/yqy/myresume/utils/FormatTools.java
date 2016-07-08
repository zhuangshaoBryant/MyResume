package com.yqy.myresume.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/** 
 * 判断一些格式的工具类
 *  
 */  
public class FormatTools {  
  
    /** 
     * 判断姓名 
     */  
    public static boolean isName(String name) {  
        String str = "^[\u4e00-\u9fa5]{2,5}$";  
        Pattern p = Pattern.compile(str);  
        Matcher m = p.matcher(name);  
        return m.matches();  
    }  
      
    /**** 
     * 判断客户名称 和全名  
     * 匹配中文，英文字母和数字及_  , 同时判断长度 2-15 
     * ***/  
    public static boolean isNameCus(String name)  
    {  
        String str = "[\u4e00-\u9fa5_a-zA-Z0-9_]{2,15}" ;  
        Pattern p = Pattern.compile(str);  
        Matcher m = p.matcher(name);  
        return m.matches();  
    }  
      
      
    /** 
     * 判断网址 
     */  
    public static boolean isWebSite(String name) {  
        String str = "\\.[a-zA-Z]{3}$";  
        Pattern p = Pattern.compile(str);  
        Matcher m = p.matcher(name);  
        return m.matches();  
    }  
      
    /** 
     * 判断邮箱 
     *  
     * @param email 
     * @return 
     */  
    public static boolean isEmail(String email) {  
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";  
        Pattern p = Pattern.compile(str);  
        Matcher m = p.matcher(email);  
  
        return m.matches();  
    }  
  
    // 判断手机号  
    public static boolean IsPhoneNum(String mobiles) {  
        if (mobiles.trim().length() == 11) {  
            Pattern p = Pattern  
                    .compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");  
            Matcher m = p.matcher(mobiles);  
            Log.d("IsPhoneNum", m.matches() + "");  
            return m.matches();  
        }  
        return false;  
  
    }  
  
    // 判断座机  
    public static boolean IsCallNum(String mobiles) {  
        boolean isValid = false;  
        CharSequence inputStr = mobiles;  
        String expression = "^(0\\d{2,3})(\\d{7,8})(\\d{3,})?$";  
        Pattern pattern = Pattern.compile(expression);  
        Matcher matcher = pattern.matcher(inputStr);  
        if (matcher.matches()) {  
            isValid = true;  
        }  
        return isValid;  
    }  
  
    // 判断手机或座机  
    public static boolean IsAllCallNum(String mobiles) {  
        boolean isValid = false;  
        String expression = "(^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$)|"  
                + "(^(0\\d{2,3})(\\d{7,8})(\\d{3,})?$)";  
        CharSequence inputStr = mobiles;  
        Pattern pattern = Pattern.compile(expression);  
        Matcher matcher = pattern.matcher(inputStr);  
        if (matcher.matches()) {  
            isValid = true;  
        }  
        return isValid;  
    }  
  
    // 判断字符串是数字  
    public static boolean isNumeric(String str) {  
        for (int i = 0; i < str.length(); i++) {  
            // System.out.println(str.charAt(i));  
            if (!Character.isDigit(str.charAt(i))) {  
                return false;  
            }  
        }  
        return true;  
    }  
  
    /** 
     * 判断是否为整数 
     *  
     * @param str 
     *            传入的字符串 
     *  
     * @return 是整数返回true,否则返回false 
     */  
  
    public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
    }  
  
    /** 
     * 判断是否符合密码规则 6-16为字母数字集合 不包含非法字符 
     *  
     * @param str 
     * @return 
     */  
    public static boolean isPwd(String str) {  
        Pattern pattern = Pattern.compile("^[a-z0-9A-Z]+$");  
        return pattern.matcher(str).matches();  
    }  
  
    /** 
     * 将秒转换成小时分钟 
     *  
     * @param second 
     * @return 
     */  
    public static String changeTotime(int second) {  
        int h = 0;  
        int d = 0;  
        int s = 0;  
        int temp = second % 3600;  
        if (second > 3600) {  
            h = second / 3600;  
            if (temp != 0) {  
                if (temp > 60) {  
                    d = temp / 60;  
                    if (temp % 60 != 0) {  
                        s = temp % 60;  
                    }  
                } else {  
                    s = temp;  
                }  
            }  
        } else {  
            d = second / 60;  
            if (second % 60 != 0) {  
                s = second % 60;  
            }  
        }  
  
        // return h + "小时" + d + "分钟" + s + "秒";  
        return h + "小时" + d + "分钟";  
    }  
  
    /** 
     * 判断是否包含非法字符 & / 
     *  
     * @return 
     */  
    public static boolean isContent(String content) {  
        if (content.contains("&") || content.contains("/")) {  
            return true;  
        }  
        return false;  
    }  
  
    /** 
     * 米转换成公里 
     */  
    public static String miToGl(int distance) {  
        double dis = Math.round(distance / 100d) / 10d;  
        return dis + "公里";  
    }  
  
    /** 
     * 匹配非表情符号的正则表达式 
     */  
    public static boolean notMood(String str_mood) {  
        boolean isValid = false;  
        CharSequence inputStr = str_mood;  
        String expression = "^([a-z]|[A-Z]|[0-9]|[\u2E80-\u9FFF]){3,}|@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?|[wap.]{4}|[www.]{4}|[blog.]{5}|[bbs.]{4}|[.com]{4}|[.cn]{3}|[.net]{4}|[.org]{4}|[http://]{7}|[ftp://]{6}$|(^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$)|(^[0-9a-zA-Z\u4E00-\u9FA5]+)";  
        Pattern pattern = Pattern.compile(expression);  
        Matcher matcher = pattern.matcher(inputStr);  
        if (matcher.matches()) {  
            isValid = true;  
        }  
        return isValid;  
    }  
}