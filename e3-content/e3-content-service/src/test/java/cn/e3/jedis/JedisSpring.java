package cn.e3.jedis;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

public class JedisSpring {

	/**
	 * 需求：jedis整合spring，测试redis集群
	 */
	@Test
	public void springWithJedis(){
		
		//加载spring配置文件
		ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("classpath*:spring/jedis.xml");
		//获取集群对象
		JedisCluster jc = app.getBean(JedisCluster.class);
		//设置值
		jc.set("address", "武当山");
	}
}
