package com.paradise.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.paradise.pojo.LoginUserInfo;
import com.paradise.pojo.User;
import com.paradise.pojo.UserResponseInfo;
import com.paradise.service.UserService;
import com.paradise.util.CookieUtil;
import com.paradise.util.MD5;
import com.paradise.util.VerifyCodeUtils;

@EnableAutoConfiguration
@RestController
public class LoginController{

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv,HttpServletRequest request) throws Exception{
		
		//User user = null;
		
		//if(CookieUtil.hasCookie(request, "UserCookie")){
		//	user = CookieUtil.readCookie(request, "UserCookie");
		//}
		
		//mv.addObject("user", "test");

		mv.setViewName("/html/login2.html");
		return mv;
	}
	
	@RequestMapping("/loginCheck")
	public @ResponseBody UserResponseInfo loginCheck(@RequestBody LoginUserInfo userInfo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session = request.getSession();
		String verifyCode = userInfo.getVerifyCode().toLowerCase();
		
		String rightVerifyCode = ((String) session.getAttribute("verifyCode")).toLowerCase();
		
		UserResponseInfo result = new UserResponseInfo();
		
		if(rightVerifyCode == null || rightVerifyCode == "" || !rightVerifyCode.equals(verifyCode)){
			
			result.setStatus(UserResponseInfo.STATUS_VERIFY_CODE_ERROR);
			result.setMessage("验证码错误！");
			
			return result;
		}
		
		User user = new User();
		user.setUserName(userInfo.getUserName());
		user.setPassword(MD5.getMD5String(userInfo.getPassword()));

		boolean r = userService.checkUserExist(user);
		
		if(r){
			
			session.setAttribute("userName", user.getUserName());
			
			if(userInfo.getRemember() == 1){
				CookieUtil.saveCookie(user, response, "UserCookie", 60 * 60 * 24 * 7);
			}
			
		}
		else{
			result.setStatus(UserResponseInfo.STATUS_ERROR);
			result.setMessage("用户名或密码错误！");
		}
		
		return result;
		
	}
	
	@RequestMapping("/checkUserExist")
	public @ResponseBody UserResponseInfo checkUserExit(@RequestBody User user) throws Exception{
		
		UserResponseInfo result = new UserResponseInfo();
		
		boolean r = userService.checkUserExist(user);
		
		if(r){
			result.setStatus(UserResponseInfo.STATUS_USER_EXIST_ERROR);
			result.setMessage("用户已存在");
		}
		
		return result;
	}
	
	@RequestMapping("/registAcount")
	public @ResponseBody UserResponseInfo registAcount(@RequestBody User user){
		
		UserResponseInfo result = new UserResponseInfo();
		user.setPassword(MD5.getMD5String(user.getPassword()));
		
		try {
			userService.addUser(user);
		} catch (Exception e) {
			result.setStatus(UserResponseInfo.STATUS_ERROR);
			result.setMessage("注册失败，请稍后重试。。，");
		}
		
		result.setMessage("注册成功！返回登录页面。。。");
		
		return result;
	}
	
	@RequestMapping("/getVerifyCode")
	public void createVerifyCode(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg"); 
		
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4); 
        
        HttpSession session = request.getSession(true); 
        session.setAttribute("verifyCode", verifyCode.toLowerCase());
        
        int w = 146, h = 33;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        
	}

}
