package cn.e3.user.service;

import cn.e3.pojo.TbUser;
import cn.e3.utils.E3mallResult;

public interface UserService {

	/**
	 * 需求：用户注册数据的校验
	 * 校验用户名是否被占用 type=1
	 * 邮箱是否被注册 type=2
	 * 手机号是否被占用 type=3
	 * 参数：String param.Integer type
	 * 返回值：E3mallResult
	 */
	public E3mallResult checkData(String param, Integer type);
	
	/**
	 * 需求：用户注册
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
	public E3mallResult register(TbUser user);
	
	/**
	 * 需求：用户登陆
	 * 参数：String uaername, String password
	 * 返回值：E3mallResult.ok(token)
	 */
	public E3mallResult login(String uaername, String password);
	
	/**
	 * 需求：根据token查询用户身份信息
	 * 参数：String token
	 * 返回值：E3mallResult.ok(User)
	 * {
		status: 200
		msg: "OK"
		data: "{"id":1,"username":"zhangzhijun","phone":"15800807944",
		"email":"420840806@qq.com","created":1414119176000,"updated":1414119179000}"
		}
	 */
	public E3mallResult findUserByToken(String token);
	
	/**
	 * 需求：根据token删除用户信息（退出）
	 * 参数：String token
	 * 返回值：
	 * {
		status: 200
		msg: "OK"
		data: ""
		}
	 */
	public E3mallResult deleteUserByToken(String token);
}
