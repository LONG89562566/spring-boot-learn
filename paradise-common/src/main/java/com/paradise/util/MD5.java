package com.paradise.util;

import java.security.MessageDigest;

public class MD5 {
	public static String getMD5String(String str){
		
		String resultStr = "";
		
		if(str == null || str == "")
			return resultStr;
		
		MessageDigest md = null;
		byte[] result = null;
		try {
			md = MessageDigest.getInstance("MD5");
			result = md.digest(str.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 resultStr = new String(result);
		
		 return resultStr;
	}
}
