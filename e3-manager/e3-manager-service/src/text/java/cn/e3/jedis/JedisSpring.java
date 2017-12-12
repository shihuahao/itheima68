package cn.e3.jedis;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

public class JedisSpring {

	/**
	 * 需求：Spring整合连接redis集群
	 */
	@Test
	public void linkRedisSpring(){
		//加载spring配置文件
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/jedis.xml");
		//获取连接池对象
		JedisCluster jedisCluster = applicationContext.getBean(JedisCluster.class);
		//设置值
		jedisCluster.set("username", "小王轩");
		//获取值
		String username = jedisCluster.get("username");
		
		System.out.println(username);
	}
}
