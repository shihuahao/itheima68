package cn.e3.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.pojo.TbUser;
import cn.e3.user.service.UserService;
import cn.e3.utils.CookieUtils;
import cn.e3.utils.E3mallResult;

@Controller
public class UserController {
	
	//注册service服务对象
	@Autowired
	private UserService userService;
	//注入cookie存储用户登陆token唯一标识
	@Value("${E3_TOKEN}")
	private String E3_TOKEN;
	//注入cookie中token过期时间
	@Value("${COOKIE_KEY_EXPIRE_TIME}")
	private Integer COOKIE_KEY_EXPIRE_TIME;

	/**
	 * 需求：用户注册数据的校验
	 * 校验用户名是否被占用 type=1
	 * 邮箱是否被注册 type=2
	 * 手机号是否被占用 type=3
	 * 请求：/user/check/{param}/{type}
	 * 参数：String param.Integer type
	 * 返回值：json格式E3mallResult
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public E3mallResult checkData(@PathVariable String param, @PathVariable Integer type){
		
		//调用远程服务对象方法
		E3mallResult result = userService.checkData(param, type);
		
		return result;
	}
	
	/**
	 * 需求：用户注册
	 * 请求：/user/register
	 * 参数：TbUser user
	 * 返回值：
	 * 失败时：
	 * {
		status: 400
		msg: "注册失败. 请校验数据后请再提交数据."
		data: null
		}
		成功时：
		{
		status: 200
		msg: "注册成功."
		data: null
		}
	 */
	@RequestMapping("/user/register")
	@ResponseBody
	public E3mallResult register(TbUser user){
		
		//调用远程service方法实现注册
		E3mallResult result = userService.register(user);
		
		return result;
	}
	
	/**
	 * 需求：用户登陆
	 * 请求：/user/longin
	 * 参数：String uaername, String password
	 * 返回值：E3mallResult.ok(token)
	 * 业务流程：
	 * 1，接收返回token
	 * 2，把token写入cookie
	 * 3，设置cookie过期时间
	 * cookie数据结构：key：value
	 * key：E3_TOKEN
	 * value：token
	 * 用户身份信息过期时间：
	 * 12小时 43200s
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public E3mallResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){
		
		//调用远程service方法
		E3mallResult result = userService.login(username, password);
		//获取token
		String token = (String) result.getData();
		//把token写入cookie
		CookieUtils.setCookie(request, response, E3_TOKEN, token, COOKIE_KEY_EXPIRE_TIME, true);
		
		return result;
	}
	
	/**
	 * 需求：根据token查询用户身份信息
	 * 请求：/user/token/{token}
	 * "http://localhost:8089/user/token/" + _ticket
	 * 参数：String token
	 * 返回值：E3mallResult.ok(User)
	 * {
		status: 200
		msg: "OK"
		data: "{"id":1,"username":"zhangzhijun","phone":"15800807944",
		"email":"420840806@qq.com","created":1414119176000,"updated":1414119179000}"
		}
		业务：
		1，从redis服务器查询用户身份信息
		2，过期时间重置
	 */
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object findUserByToken(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response){
		//调用远程service服务
		E3mallResult result = userService.findUserByToken(token);
		if(result.getData()!=null){
			//重置cookie过期时间
			CookieUtils.setCookie(request, response, E3_TOKEN, token, COOKIE_KEY_EXPIRE_TIME, true);
		}
		//判断是否是跨域请求
		if(StringUtils.isNotBlank(callback)){
			//是一个跨域请求
			//return "callback("+json(user)+")";
			//使用Jackson自动转换
			MappingJacksonValue mJacksonValue = new MappingJacksonValue(result);
			//设置跨域函数
			mJacksonValue.setJsonpFunction(callback);
			
			return mJacksonValue;
		}
		
		return result;
	}
	
	/**
	 * 需求：根据token删除用户信息（退出）
	 * 请求：/user/logout/{token}
	 * 参数：String token
	 * 返回值：
	 * {
		status: 200
		msg: "OK"
		data: ""
		}
	 */
	@RequestMapping("/user/logout/{token}")
	public Object logout(@PathVariable String token,String callback, HttpServletRequest request, HttpServletResponse response){
		
		//调用远程service方法
		E3mallResult result = userService.deleteUserByToken(token);
		
		//删除cookie
		CookieUtils.deleteCookie(request, response, E3_TOKEN);
		
		//判断是不是跨域请求
		if(StringUtils.isNotBlank(callback)){
			
			MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
			
			jacksonValue.setJsonpFunction(callback);
			
			return jacksonValue;
			
		}
		
		return "login";
	}
}
