package com.yu.dialog.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

public class StringUtil {
	public static boolean isNullOrWhiteSpace(String text) {
		if (text == null)
			return true;
		return text.matches("^\\s*$");
	}

	public static boolean isEqualsNoCaseEmptyOrNull(String str1, String str2) {
		if (str1 == null)
			str1 = "";
		if (str2 == null)
			str2 = "";
		return str1.equals(str2);
	}

	public static boolean toBoolean(String str) {
		if (str == null)
			return false;
		String lowerStr = str.toLowerCase();
		if (lowerStr.equals("true") || lowerStr.equals("1")) {
			return true;
		}
		return false;
	}

	public static boolean isCN(String str) {
		try {
			byte[] bytes = str.getBytes("UTF-8");
			if (bytes.length == str.length()) {
				return false;
			} else {
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getDefaultText(String srcStr,String defauleStr) {
		if(TextUtils.isEmpty(srcStr)) {
			return defauleStr;
		}
		return srcStr = srcStr.trim();
 	}
}