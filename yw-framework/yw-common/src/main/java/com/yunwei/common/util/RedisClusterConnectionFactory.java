package com.yunwei.common.util;

import java.util.Set;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisClusterConnectionFactory extends BasePooledObjectFactory<JedisCluster> {
	
	private Set<HostAndPort> hostPortSet = null;
	
	public RedisClusterConnectionFactory(Set<HostAndPort> hostPortSet) {
		this.hostPortSet = hostPortSet;
	}

	@Override
	public JedisCluster create() throws Exception {
		JedisCluster jc = new JedisCluster(hostPortSet);
		return jc;
	}

	@Override
	public PooledObject<JedisCluster> wrap(JedisCluster obj) {
		return new DefaultPooledObject<JedisCluster>(obj);
	}
}