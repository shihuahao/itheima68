package cn.e3.jedis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3.jedis.dao.JedisDao;
import redis.clients.jedis.JedisCluster;

@Repository
public class ClusterJedisDaoImpl implements JedisDao {

	//注入集群对象
	@Autowired
	private JedisCluster jc;
	
	@Override
	public String set(String key, String value) {
		String set = jc.set(key, value);
		return set;
	}

	@Override
	public String get(String key) {
		String value = jc.get(key);
		return value;
	}

	@Override
	public Long incr(String key) {
		Long incr = jc.incr(key);
		return incr;
	}

	@Override
	public Long decr(String key) {
		Long decr = jc.decr(key);
		return decr;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Long hset = jc.hset(key, field, value);
		return hset;
	}

	@Override
	public String hget(String key, String field) {
		String hget = jc.hget(key, field);
		return hget;
	}

	@Override
	public Long hdel(String key, String field) {
		Long hdel = jc.hdel(key, field);
		return hdel;
	}

	@Override
	public Long expire(String key, int seconds) {
		Long expire = jc.expire(key, seconds);
		return expire;
	}

	@Override
	public Long ttl(String key) {
		Long ttl = jc.ttl(key);
		return ttl;
	}

}
