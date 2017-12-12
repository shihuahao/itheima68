package cn.e3.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MyJedis {

	/**
	 * 需求：连接单机版Redis服务器
	 */
	@Test
	public void linkSingleRedisWithOutPool(){
		//创建jedis对象，连接单机版redis服务器
		Jedis jedis = new Jedis("192.168.213.129", 6379);
		//添加值
		jedis.set("username", "张三丰天下无敌！");
		
	}
	
	/**
	 * 需求：连接Redis服务器
	 */
	@Test
	public void linkSingleRedisWithPool(){
		//创建连接池配置对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//设置最大连接数
		poolConfig.setMaxTotal(2000);
		poolConfig.setMaxIdle(20);
		
		//创建连接池对象
		JedisPool pool = new JedisPool(poolConfig,"192.168.213.129", 6379);
		
		//从连接池获取对象
		Jedis jedis = pool.getResource();
		
		//添加值
		jedis.set("username", "张三丰天下无敌！桑无极");
		
	}
	
	
	/**
	 * 需求：连接集群Redis服务器
	 */
	@Test
	public void linkClusterleRedisWithPool(){
		//创建连接池配置对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//设置最大连接数
		poolConfig.setMaxTotal(2000);
		poolConfig.setMaxIdle(20);
		
		//创建set集合对象，封装集群节点
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.213.129", 7001));
		nodes.add(new HostAndPort("192.168.213.129", 7002));
		nodes.add(new HostAndPort("192.168.213.129", 7003));
		nodes.add(new HostAndPort("192.168.213.129", 7004));
		nodes.add(new HostAndPort("192.168.213.129", 7005));
		nodes.add(new HostAndPort("192.168.213.129", 7006));
		nodes.add(new HostAndPort("192.168.213.129", 7007));
		nodes.add(new HostAndPort("192.168.213.129", 7008));
		//创建集群对象
		JedisCluster jc = new JedisCluster(nodes,poolConfig);
		
		//添加值
		jc.set("username", "张三丰天下无敌！桑无极");
		
	}
}
