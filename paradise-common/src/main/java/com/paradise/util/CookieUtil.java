package com.paradise.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paradise.pojo.User;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;


public class CookieUtil {
	
	private static final String WEB_KEY = "123456";
	
	public static void saveCookie(User user,HttpServletResponse response, String cookieName, int expiry) throws UnsupportedEncodingException{
		
		String str = user.getUserName() + "|" +  user.getPassword() + "|" + WEB_KEY;
		
		String cookieVlue = new String(BASE64EncoderStream.encode(str.getBytes("utf-8")));
		
		Cookie cookie = new Cookie(cookieName, cookieVlue);
		
		cookie.setMaxAge(expiry);
		
		cookie.setPath("/");
		
		response.addCookie(cookie);
		
	}
	
	
	public static boolean hasCookie(HttpServletRequest request, String cookieName){
		if(cookieName == null || cookieName == "")
			return false;
		
		Cookie[] cookies = request.getCookies();
		
		for(int i = 0; i < cookies.length; i ++){
			if(cookieName.equals(cookies[i].getName())){
				return true;
			}
		}
		
		return false;
	}
	
	public static User readCookie(HttpServletRequest request, String cookieName) throws UnsupportedEncodingException{
		if(cookieName == null || cookieName == "")
			return null;
		
		Cookie[] cookies = request.getCookies();
		
		String value = null;
		
		for(int i = 0; i < cookies.length; i ++){
			if(cookieName.equals(cookies[i].getName())){
				value = cookies[i].getValue();
			}
		}
		
		if(value == null)
			return null;
		
		
		String baseStr = new String(BASE64DecoderStream.decode(value.getBytes("utf-8")));
		String[] values = baseStr.split("|");
		
		
		User user = new  User();
		
		user.setUserName(values[0]);
		
		user.setPassword(values[1]);
		
		return user;
	}
	
	
}
