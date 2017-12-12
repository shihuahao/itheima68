package cn.e3.jedis;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class MyJedis {

	/**
	 * 需求：连接单机版redis
	 */
	@Test
	public void linkReids(){
		//创建jedis对象
		Jedis jedis = new Jedis("192.168.213.129",6379);
		//设置值
		jedis.set("username", "王轩");
		//获取值
		String username = jedis.get("username");
		
		System.out.println(username);
	}
	
	/**
	 * 需求：连接池连接redis
	 */
	@Test
	public void linkPoolReids(){
		//创建连接池对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//设置参数
		poolConfig.setMaxTotal(2000);
		poolConfig.setMaxIdle(20);
		//获取连接池对象
		JedisPool jp = new JedisPool(poolConfig, "192.168.213.129", 6379);
		//从连接池中获取jedis对象
		Jedis jedis = jp.getResource();
		//设置值
		jedis.set("username", "王轩轩");
		//获取值
		String username = jedis.get("username");
		
		System.out.println(username);
	}
	
	/**
	 * 需求：jedis连接redis集群
	 */
	@Test
	public void linkReidsCluster(){
		//创建连接池对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		//设置参数
		poolConfig.setMaxTotal(2000);
		poolConfig.setMaxIdle(20);
		
		HashSet<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.213.129", 7001));
		nodes.add(new HostAndPort("192.168.213.129", 7002));
		nodes.add(new HostAndPort("192.168.213.129", 7003));
		nodes.add(new HostAndPort("192.168.213.129", 7004));
		nodes.add(new HostAndPort("192.168.213.129", 7005));
		nodes.add(new HostAndPort("192.168.213.129", 7006));
		nodes.add(new HostAndPort("192.168.213.129", 7007));
		nodes.add(new HostAndPort("192.168.213.129", 7008));
		//创建一个集群核心对象
		JedisCluster jedisCluster = new JedisCluster(nodes,poolConfig);
		//设置值
		jedisCluster.set("username", "王轩轩轩");
		//获取值
		String username = jedisCluster.get("username");
		
		System.out.println(username);
	}
}
