package com.yunwei.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwei.common.user.SysUser;

import redis.clients.jedis.AdvancedJedisCommands;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.MultiKeyCommands;
import redis.clients.util.Pool;

/**
 * 功能说明: redis工具类<br>
 * 系统版本: v1.0<br>
 * 开发人员: @author kongdy<br>
 * 开发时间: 2015年8月4日<br>
 */
public class RedisClientUtil {

	protected static Logger logger = LoggerFactory.getLogger(RedisClientUtil.class);

	private static Pool<Jedis> jedisPool;

	private static GenericObjectPool<JedisCluster> clusterPool;

	/** 设置成功返回值 */
	private static final String OK = "OK";
	
	//Redis服务器IP
	//private static String ADDR = "39.100.96.124";
    private static String ADDR = "39.106.89.216";
 
    //Redis的端口号
    private static int PORT = 6379;
 
    //访问密码
    private static String AUTH = "123456";
 
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;
 
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
 
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
 
    private static int TIMEOUT = 10000;
 
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

//	/**
//	 * 获取jedis-sentinel连接实例，取ifs.properties配置项“redis.sentinel.address”
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static Pool<Jedis> getJedisPool() {
//		if (jedisPool != null) {
//			return jedisPool;
//		}
//		Map<String, Object> configMap = parseConfigXml();
//		Set<String> sentinels = (Set<String>)configMap.get("servers");
//		JedisPoolConfig config = new JedisPoolConfig();
//		// 最大连接数, 默认8个
//		config.setMaxTotal(Integer.parseInt((String)configMap.get("maxConn")));
//		// 最大空闲连接数, 默认8个
//		config.setMaxIdle(Integer.parseInt((String)configMap.get("maxIdle")));
//		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常,
//		// 小于零:阻塞不确定的时间, 默认-1
//		config.setMaxWaitMillis(Long.parseLong((String)configMap.get("maxBusyTime")));
//		int timeout = Integer.parseInt((String)configMap.get("timeout"));
//		// 密码
//		String password = (String)configMap.get("password");
//
//		try {
//			jedisPool = new JedisSentinelPool((String)configMap.get("name"), sentinels, config, timeout, password);
//			logger.info(String.format("连接redis-sentinel服务器[%s]", sentinels));
//		} catch (Exception e) {
//			logger.error("连接redis服务器发生错误", e);
//		}
//		return jedisPool;
//	}

	/**
	 * 获取redis实例
	 * @return
	 */
	public static Pool<Jedis> getJedisPool() {
		if (jedisPool != null) {
			return jedisPool;
		}
		// 配置redis实例池
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);

		try {
//			jedisPool = new JedisSentinelPool((String)configMap.get("name"), sentinels, config, timeout, password);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
			logger.info(String.format("连接redis服务器[%s]", jedisPool));
		} catch (Exception e) {
			logger.error("连接redis服务器发生错误", e);
		}
		return jedisPool;
	}
	
	/**
	 * 获取jedis-culster连接实例
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static GenericObjectPool<JedisCluster> getClusterPool() {
		if (clusterPool != null) {
			return clusterPool;
		}
		Map<String, Object> configMap = parseConfigXml();
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		Set<String> servers = (HashSet<String>)configMap.get("servers");
		if ((servers != null) && (servers.size() > 0)) {
			for (String server : servers) {
				String[] tmp = server.split(":");
				jedisClusterNodes.add(new HostAndPort(tmp[0], Integer.parseInt(tmp[1])));
			}
		}

		RedisClusterConnectionFactory factory = new RedisClusterConnectionFactory(jedisClusterNodes);
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		// 最大连接数, 默认8个
		config.setMaxTotal(Integer.parseInt((String)configMap.get("maxConn")));
		// 最大空闲连接数, 默认8个
		config.setMaxIdle(Integer.parseInt((String)configMap.get("maxIdle")));
		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常,
		// 小于零:阻塞不确定的时间, 默认-1
		config.setMaxWaitMillis(Long.parseLong((String)configMap.get("maxBusyTime")));
		try {
			clusterPool = new GenericObjectPool<JedisCluster>(factory, config);
			logger.info(String.format("连接redis-cluster服务器"));
		} catch (Exception e) {
			logger.error("连接redis服务器发生错误", e);
		}
		return clusterPool;
	}

//	/**
//	 * 解析redis配置
//	 * @return
//	 */
//	public static Map<String, Object> parseConfigXml() {
//		Map<String, Object> map = new HashMap<String, Object>();
//		SAXReader reader = new SAXReader();
//		reader.setEncoding("utf-8");
//		String filePath;
//		try {
//			filePath = EnvironmentUtils.getFileAbsolutePath("/redis/jedis.xml");
//			Document doc = reader.read(filePath);
//			Element group = doc.getRootElement().element("serverGroup");
//			for (Object obj : group.attributes()) {
//				if (obj instanceof Attribute) {
//					Attribute attr = (Attribute)obj;
//					map.put(attr.getName(), attr.getValue());
//				}
//			}
//			Set<String> servers = new HashSet<String>();
//			for (Object obj : group.elements("server")) {
//				if (obj instanceof Element) {
//					Element ele = (Element)obj;
//					servers.add(ele.attributeValue("ip") + ":" + ele.attributeValue("port"));
//				}
//			}
//			map.put("servers", servers);
//			if (servers.isEmpty()) {
//				logger.error("redis缓存服务器未配置，请正确配置");
//				System.exit(0);
//			}
//		} catch (FileNotFoundException | DocumentException e) {
//			logger.error("redis缓存服务器未配置，请正确配置", e);
//			System.exit(0);
//		}
//		return map;
//	}

	/**
	 * 解析redis配置
	 * @return
	 */
	public static Map<String, Object> parseConfigXml() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * 关闭连接池
	 */
	public static void close() {
		if (jedisPool != null) {
			jedisPool.close();
			jedisPool.destroy();
		}
		if (clusterPool != null) {
			clusterPool.close();
		}
	}
	
	/**
	 * 生成存储实体对象的key
	*
	*@param pojoname	实体名
	*@param oldKey		原始key
	*@return			新key
	*String
	 */
	public static String getModelKeyAlias(String pojoname , String oldKey){
		
		return pojoname + "-" + oldKey;
	}
	
	/**
	 * 判断是否存在
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Boolean exists(String key) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return false;
			}
			Jedis jedis = pool.getResource();
			try {
				return jedis.exists(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				return jedisCluster.exists(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}
	
	/**
	 * 单个对象序列化至redis中
	 * @param key	key
	 * @param obj	对象
	 */
	@SuppressWarnings("deprecation")
	public static void add(String key, Object obj) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool != null && obj != null && obj instanceof Serializable) {
				Jedis jedis = pool.getResource();
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(obj);
					byte[] bytes = baos.toByteArray();
					jedis.set(key.getBytes(), bytes);
				} catch (IOException e) {
					logger.error("将对象序列化至redis中异常", e);
				} finally {
					pool.returnResource(jedis);
				}
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool != null && obj != null && obj instanceof Serializable) {
				JedisCluster jedisCluster = null;
				try {
					jedisCluster = clusterPool.borrowObject();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(obj);
					byte[] bytes = baos.toByteArray();
					jedisCluster.set(key.getBytes(), bytes);
				} catch (IOException e) {
					logger.error("将对象序列化至redis中异常", e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					return;
				} finally {
					clusterPool.returnObject(jedisCluster);
				}
			}
		}
	}

	/**
	 * 将redis中数据反序列化成对象
	 * @param key
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings({"deprecation", "unchecked"})
	public static <T> T getObj(String key) {
		T t = null;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool != null) {
				Jedis jedis = pool.getResource();
				try {
					byte[] value = jedis.get(key.getBytes());
					ByteArrayInputStream bis = new ByteArrayInputStream(value);
					ObjectInputStream ois = new ObjectInputStream(bis);
					t = (T)ois.readObject();
				} catch (Exception e) {
					logger.error("将redis中数据序列化为对象异常", e);
				} finally {
					pool.returnResource(jedis);
				}
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool != null) {
				JedisCluster jedisCluster = null;
				try {
					jedisCluster = clusterPool.borrowObject();
					byte[] value = jedisCluster.get(key.getBytes());
					ByteArrayInputStream bis = new ByteArrayInputStream(value);
					ObjectInputStream ois = new ObjectInputStream(bis);
					t = (T)ois.readObject();
				} catch (Exception e) {
					logger.error("将redis中数据序列化为对象异常", e);
				} finally {
					clusterPool.returnObject(jedisCluster);
				}
			}
		}
		return t;
	}
	
	/**
	 * List集合序列化至redis中
	 * @param key	key
	 * @param list	集合
	 */
	@SuppressWarnings("deprecation")
	public static void addList(String key, List<?> list) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool != null && list.size() > 0 && list.get(0) instanceof Serializable) {
				Jedis jedis = pool.getResource();
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					for(Object obj : list){
						oos.writeObject(obj);
					}
					byte[] bytes = baos.toByteArray();
					jedis.set(key.getBytes(), bytes);
				} catch (IOException e) {
					logger.error("将对象序列化至redis中异常", e);
				} finally {
					pool.returnResource(jedis);
				}
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool != null && list.size() > 0 && list.get(0) instanceof Serializable) {
				JedisCluster jedisCluster = null;
				try {
					jedisCluster = clusterPool.borrowObject();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					for(Object obj : list){
						oos.writeObject(obj);
					}
					byte[] bytes = baos.toByteArray();
					jedisCluster.set(key.getBytes(), bytes);
				} catch (IOException e) {
					logger.error("将对象序列化至redis中异常", e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					return;
				} finally {
					clusterPool.returnObject(jedisCluster);
				}
			}
		}
	}
	
	/**
	 * 将redis中数据反序列化成list集合
	 * @param key
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings({"deprecation", "unchecked"})
	public static List<?> getObjList(String key) {
		List<Object> t = new ArrayList<Object>();
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool != null) {
				Jedis jedis = pool.getResource();
				try {
					byte[] value = jedis.get(key.getBytes());
					ByteArrayInputStream bis = new ByteArrayInputStream(value);
					ObjectInputStream ois = new ObjectInputStream(bis);
					while (bis.available() > 0) {
						Object obj = (Object) ois.readObject();
						if (obj == null) {
	                        break;
	                    }
	                    t.add(obj);
					}
				} catch (Exception e) {
					logger.error("将redis中数据序列化为对象异常", e);
				} finally {
					pool.returnResource(jedis);
				}
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool != null) {
				JedisCluster jedisCluster = null;
				try {
					jedisCluster = clusterPool.borrowObject();
					byte[] value = jedisCluster.get(key.getBytes());
					ByteArrayInputStream bis = new ByteArrayInputStream(value);
					ObjectInputStream ois = new ObjectInputStream(bis);
					while (bis.available() > 0) {
						Object obj = (Object) ois.readObject();
						if (obj == null) {
	                        break;
	                    }
	                    t.add(obj);
					}
				} catch (Exception e) {
					logger.error("将redis中数据序列化为对象异常", e);
				} finally {
					clusterPool.returnObject(jedisCluster);
				}
			}
		}
		return t;
	}

	/**
	 * 写入/覆盖。如果key已经在redis上存在，那么会被覆盖。
	 * @param key
	 *        键
	 * @param value
	 *        值。 注意不能超过1G
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean set(String key, String value) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return result;
			}
			Jedis jedis = pool.getResource();
			try {
				if (value != null) {
					logger.debug("写入或覆盖的key={}，value={}", key, value);
					String rtn = jedis.set(key, value);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return result;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				if (value != null) {
					logger.debug("写入或覆盖的key={}，value={}", key, value);
					String rtn = jedisCluster.set(key, value);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 写入/覆盖。如果key已经在redis上存在，那么会被覆盖。
	 * @param key
	 * @param value
	 * @param seconds
	 *        存活时间（秒）
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean set(String key, String value, int seconds) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return result;
			}
			Jedis jedis = pool.getResource();
			try {
				if (value != null) {
					logger.debug("写入或覆盖的key={}，value={}，seconds={}", key, value, seconds);
					String rtn = jedis.setex(key, seconds, value);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return result;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				if (value != null) {
					logger.debug("写入或覆盖的key={}，value={}，seconds={}", key, value, seconds);
					String rtn = jedisCluster.setex(key, seconds, value);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 对value参数做toString处理，{@link #set(String, String, int)}
	 * @see #set(String, String, int)
	 * @author meijie
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public static boolean set(String key, Object value, int seconds) {
		if (value != null) {
			return set(key, value.toString(), seconds);
		} else {
			return set(key, value, seconds);
		}
	}

	/**
	 * 不存在key 则写入。即：set if Not eXists
	 * <p>
	 * 如果redis上已经有key，什么都不错; 如果redis上没有key，则写入;
	 * </p>
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean setnx(String key, String value) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return result;
			}
			Jedis jedis = pool.getResource();

			try {
				if (value != null) {
					logger.debug("若不存在该key则写入，写入的key={}，value={}", key, value);
					if (jedis.setnx(key, value) > 0) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return result;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				if (value != null) {
					logger.debug("若不存在该key则写入，写入的key={}，value={}", key, value);
					if (jedisCluster.setnx(key, value) > 0) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 不存在key 则写入。即：set if Not eXists
	 * <p>
	 * 如果redis上已经有key，什么都不错; 如果redis上没有key，则写入;
	 * </p>
	 * @param key
	 * @param value
	 * @param seconds
	 *        存活时间（秒）
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean setnx(String key, String value, int seconds) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return result;
			}
			Jedis jedis = pool.getResource();

			try {
				if (value != null) {
					// NX:只在键不存在时，才对键进行设置操作; XX:只在键已经存在时，才对键进行设置操作
					// EX:设置键的过期时间为 second秒; PX:设置键的过期时间为 millisecond 毫秒
					logger.debug("若不存在该key则写入，写入的key={}，value={}，seconds={}", key, value, seconds);
					String rtn = jedis.set(key, value, "NX", "EX", seconds);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return result;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				if (value != null) {
					// NX:只在键不存在时，才对键进行设置操作; XX:只在键已经存在时，才对键进行设置操作
					// EX:设置键的过期时间为 second秒; PX:设置键的过期时间为 millisecond 毫秒
					logger.debug("若不存在该key则写入，写入的key={}，value={}，seconds={}", key, value, seconds);
					String rtn = jedisCluster.set(key, value, "NX", "EX", seconds);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 更新。key存在时，才更新覆盖。
	 * <p>
	 * 如果redis上没有key，什么都不错; 如果redis上有key，则更新;
	 * </p>
	 * @param key
	 * @param value
	 * @param seconds
	 *        存活时间（秒）
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean setxx(String key, String value, int seconds) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return result;
			}
			Jedis jedis = pool.getResource();

			try {
				if (value != null) {
					// NX:只在键不存在时，才对键进行设置操作; XX:只在键已经存在时，才对键进行设置操作
					// EX:设置键的过期时间为 second秒; PX:设置键的过期时间为 millisecond 毫秒
					logger.debug("键已经存在时才对键进行操作，写入的key={}，value={}，seconds={}", key, value, seconds);
					String rtn = jedis.set(key, value, "XX", "EX", seconds);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return result;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				if (value != null) {
					// NX:只在键不存在时，才对键进行设置操作; XX:只在键已经存在时，才对键进行设置操作
					// EX:设置键的过期时间为 second秒; PX:设置键的过期时间为 millisecond 毫秒
					logger.debug("键已经存在时才对键进行操作，写入的key={}，value={}，seconds={}", key, value, seconds);
					String rtn = jedisCluster.set(key, value, "XX", "EX", seconds);
					if (StringUtils.equals(OK, rtn)) {
						result = true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 删除
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean del(String key) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return false;
			}
			Jedis jedis = pool.getResource();
			try {
				logger.debug("删除的key为key={}", key);
				jedis.del(key);
				return true;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				logger.debug("删除的key为key={}", key);
				jedisCluster.del(key);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	/**
	 * 读取
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String get(String key) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return null;
			}
			Jedis jedis = pool.getResource();
			try {
				logger.debug("读取的key为key={}", key);
				return jedis.get(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return null;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				logger.debug("读取的key为key={}", key);
				return jedisCluster.get(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	/**
	 * 批量读取
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<String> mget(String[] key) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return null;
			}
			Jedis jedis = pool.getResource();
			try {
				return jedis.mget(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return null;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				return jedisCluster.mget(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	/**
	 * 根据type将id增加到set集合
	 * @param type
	 * @param score
	 * @param id
	 *        开发人员: @author huadi<br>
	 *        开发时间: 2015年10月14日<br>
	 */
	@SuppressWarnings("deprecation")
	public static void addToRedisSetById(String type, Double score, String id) {
		Map<String, Object> configMap = parseConfigXml();
		String redisType = (String)configMap.get("type");
		if (StringUtils.isBlank(redisType) || "sentinels".equals(redisType)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				logger.info("根据type将id增加到set集合出错:无法获取redis连接");
				return;
			}
			Jedis jedis = pool.getResource();
			try {
				jedis.zadd(type, score, id);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				logger.info("根据type将id增加到set集合出错:无法获取redis连接");
				return;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.zadd(type, score, id);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	/**
	 * 根据type将id从set集合中删除
	 * @param type
	 * @param id
	 *        开发人员: @author huadi<br>
	 *        开发时间: 2015年10月14日<br>
	 */
	@SuppressWarnings("deprecation")
	public static void removeFromRedisSetById(String type, String id) {
		Map<String, Object> configMap = parseConfigXml();
		String redisType = (String)configMap.get("type");
		if (StringUtils.isBlank(redisType) || "sentinels".equals(redisType)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				logger.info("根据type将id从set集合中删除出错:无法获取redis连接");
				return;
			}
			Jedis jedis = pool.getResource();
			try {
				jedis.zrem(type, id);
				if (StringUtils.equals(type, "onlineEmp")) {
					logger.info("从[" + type + "]集合中删除坐席[emp_id =" + id + "]成功!");
				} else {
					logger.info("从[" + type + "]集合中删除用户[user_id =" + id + "]成功!");
				}
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				logger.info("根据type将id从set集合中删除出错:无法获取redis连接");
				return;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.zrem(type, id);
				if (StringUtils.equals(type, "onlineEmp")) {
					logger.info("从[" + type + "]集合中删除坐席[emp_id =" + id + "]成功!");
				} else {
					logger.info("从[" + type + "]集合中删除用户[user_id =" + id + "]成功!");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	/**
	 * 根据类型获取所属的id集合
	 * @param type
	 * @return set<id>
	 *         开发人员: @author huadi<br>
	 *         开发时间: 2015年10月14日<br>
	 */
	@SuppressWarnings("deprecation")
	public static Set<String> getSortedSet(String type) {
		Set<String> userSet = null;
		Map<String, Object> configMap = parseConfigXml();
		String redisType = (String)configMap.get("type");
		if (StringUtils.isBlank(redisType) || "sentinels".equals(redisType)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				logger.info("根据类型获取所属的id集合出错:无法获取redis连接");
				return userSet;
			}
			Jedis jedis = pool.getResource();
			try {
				userSet = jedis.zrange(type, 0L, -1L);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				logger.info("根据类型获取所属的id集合出错:无法获取redis连接");
				return userSet;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				userSet = jedisCluster.zrange(type, 0L, -1L);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return userSet;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return userSet;
	}

	/**
	 * 根据类型获取Set总数
	 * @param type
	 * @return
	 *         开发人员: @author huadi<br>
	 *         开发时间: 2015年10月14日<br>
	 */
	@SuppressWarnings("deprecation")
	public static int getSetCount(String type) {
		int count = 0;
		Map<String, Object> configMap = parseConfigXml();
		String redisType = (String)configMap.get("type");
		if (StringUtils.isBlank(redisType) || "sentinels".equals(redisType)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				logger.info("根据类型获取Set总数出错:无法获取redis连接");
				return count;
			}
			Jedis jedis = pool.getResource();
			try {
				count = jedis.zcard(type).intValue();
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				logger.info("根据类型获取Set总数出错:无法获取redis连接");
				return count;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				count = jedisCluster.zcard(type).intValue();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return count;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return count;
	}

	/**
	 * 根据Id从set中查询score(权重)
	 * @param type
	 * @param id
	 * @return
	 *         开发人员: @author huadi<br>
	 *         开发时间: 2015年10月14日<br>
	 */
	@SuppressWarnings("deprecation")
	public static Double getScoreById(String type, String id) {
		Double score = null;
		Map<String, Object> configMap = parseConfigXml();
		String redisType = (String)configMap.get("type");
		if (StringUtils.isBlank(redisType) || "sentinels".equals(redisType)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				logger.info("根据Id从set中查询score(权重)出错:无法获取redis连接");
				return score;
			}
			Jedis jedis = pool.getResource();
			try {
				score = jedis.zscore(type, id);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				logger.info("根据Id从set中查询score(权重)出错:无法获取redis连接");
				return score;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				score = jedisCluster.zscore(type, id);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return score;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return score;
	}

	/**
	 * 根据样式查找
	 * @param pattern
	 * @return
	 *         开发人员: @author huadi<br>
	 *         开发时间: 2015年10月14日<br>
	 */
	@SuppressWarnings("deprecation")
	public static List<String> getByPattern(String pattern) {
		List<String> list = null;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				logger.info("根据样式查找出错:无法获取redis连接");
				return list;
			}
			Jedis jedis = pool.getResource();
			try {
				list = jedis.configGet(pattern);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				logger.info("根据样式查找出错:无法获取redis连接");
				return list;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				list = ((AdvancedJedisCommands)jedisCluster).configGet(pattern);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return list;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return list;
	}

	/**
	 * 在set集合中根据最小权重和最大权重得出中间的数目
	 * @param type
	 * @param min
	 *        最小权重
	 * @param max
	 *        最大权重
	 * @return
	 *         开发人员: @author huadi<br>
	 *         开发时间: 2015年10月14日<br>
	 */
	@SuppressWarnings("deprecation")
	public static int getSetCountByScore(String type, double min, double max) {
		Long count = 0L;
		Map<String, Object> configMap = parseConfigXml();
		String redisType = (String)configMap.get("type");
		if (StringUtils.isBlank(redisType) || "sentinels".equals(redisType)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				logger.info("统计排队位置出错:无法获取redis连接");
				return 0;
			}
			Jedis jedis = pool.getResource();
			try {
				count = jedis.zcount(type, min, max);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				logger.info("统计排队位置出错:无法获取redis连接");
				return 0;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				count = jedisCluster.zcount(type, min, max);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return 0;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return count.intValue();
	}

	/**
	 * 模糊查找key
	 * @param key
	 *        查询条件[eg: config.cache.* ]
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Set<String> keys(String key) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return null;
			}
			Jedis jedis = pool.getResource();
			try {
				return jedis.keys(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return null;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				return ((MultiKeyCommands)jedisCluster).keys(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	/**
	 * 写入
	 * @param key
	 * @param hash
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean hset(String key, String curkey, String Value, int seconds) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return result;
			}
			Jedis jedis = pool.getResource();
			try {
				jedis.hset(key, curkey, Value);
				if (seconds > 0) {
					jedis.expire(key, seconds);
				}
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return result;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.hset(key, curkey, Value);
				if (seconds > 0) {
					jedisCluster.expire(key, seconds);
				}
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 获取
	 * @param key
	 * @param hash
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String hget(String key, String field) {
		String result = null;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return null;
			}
			Jedis jedis = pool.getResource();
			try {
				result = jedis.hget(key, field);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = null;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return null;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				result = jedisCluster.hget(key, field);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = null;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		if (result != null) {
			return result;
		} else {
			return "";
		}
	}

	/**
	 * 删除
	 * @param key
	 * @param hash
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean hdel(String key, String field) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return false;
			}
			Jedis jedis = pool.getResource();
			try {
				jedis.hdel(key, field);
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.hdel(key, field);
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 设置redis hash集合
	 * @param key
	 * @param hash
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean hmset(String key, Map<String, String> hash, int seconds) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return result;
			}
			Jedis jedis = pool.getResource();
			try {
				jedis.hmset(key, hash);
				if (seconds > 0) {
					jedis.expire(key, seconds);
				}
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return result;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.hmset(key, hash);
				if (seconds > 0) {
					jedisCluster.expire(key, seconds);
				}
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 获取
	 * @param key
	 * @param hash
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Map<String, String> hgetall(String key) {
		Map<String, String> result = null;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return null;
			}
			Jedis jedis = pool.getResource();
			try {
				result = jedis.hgetAll(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = null;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return null;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				result = jedisCluster.hgetAll(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = null;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	/**
	 * 判断hset是否存在
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Boolean hexists(String key, String field_name) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return false;
			}
			Jedis jedis = pool.getResource();
			try {
				return jedis.hexists(key, field_name);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				return jedisCluster.hexists(key, field_name);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	public static void main(String[] args) {
		// set("config.cache.clients", null);
		// del("config.cache.clients");
		for (int t = 0; t < 20; t++) {
			new Thread() {

				public void run() {
					for (int i = 0; i < 1000; i++) {
						System.out.println(get("config.cache.clients"));
						try {
							System.out.println(set(null, "12345"));
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}
			}.start();
		}
		// System.out.println(get("config.cache.db"));
		// System.out.println(get("config.cache.CAConfigCache"));
		// close();
	}

	/**
	 * 读取redis，根据format参数返回Date
	 * @author meijie
	 * @param key 键
	 * @param format 日期格式化字符串
	 * @return 返回null，如果解析失败
	 */
	public static Date getDate(String key, String format) {
		String value = get(key);
		return DateUtil.parse(value, format);
	}

	/**
	 * 读取redis，解析数字并返回
	 * @author meijie
	 * @param key
	 * @param defaultValue
	 * @return 返回defaultValue，如果解析失败
	 */
	public static int getInt(String key, int defaultValue) {
		String value = get(key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 将一个值插入到列表头部，value可以重复，返回列表的长度
	 * @param key
	 * @param value String
	 * @return 返回List的长度
	 */
	@SuppressWarnings("deprecation")
	public static Long lpush(String key, String value) {
		Long length = (long)0;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			Jedis jedis = pool.getResource();

			try {
				length = jedis.lpush(key, value);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				length = jedisCluster.lpush(key, value);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return length;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return length;
	}

	/**
	 * 将多个值插入到列表头部，value可以重复
	 * @param key
	 * @param values String[]
	 * @return 返回List的数量length
	 */
	@SuppressWarnings("deprecation")
	public static Long lpush(String key, String[] values) {
		Long length = (long)0;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			Jedis jedis = pool.getResource();

			try {
				length = jedis.lpush(key, values);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				length = jedisCluster.lpush(key, values);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return length;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return length;
	}

	/**
	 * 移除并获取列表最后一个元素，当列表不存在或者为空时，返回Null
	 * @param key
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String rpop(String key) {
		String value = "";
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			Jedis jedis = pool.getResource();

			try {
				value = jedis.rpop(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				value = jedisCluster.rpop(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return value;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return value;
	}

	/**
	 * 移除列表元素，返回移除的元素数量
	 * @param key
	 * @param count，标识，表示动作或者查找方向 <li>当count=0时，移除所有匹配的元素；</li> <li>当count为负数时，移除方向是从尾到头；</li> <li>当count为正数时，移除方向是从头到尾；
	 *        </li>
	 * @param value 匹配的元素
	 * @return Long
	 */
	@SuppressWarnings("deprecation")
	public static Long lrem(String key, long count, String value) {
		Long length = (long)0;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			Jedis jedis = pool.getResource();

			try {
				length = jedis.lrem(key, count, value);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				length = jedisCluster.lrem(key, count, value);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return length;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return length;
	}

	/**
	 * 获取列表长度，key为空时返回0
	 * @param key
	 * @return Long
	 */
	@SuppressWarnings("deprecation")
	public static Long llen(String key) {
		Long length = (long)0;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			Jedis jedis = pool.getResource();

			try {
				length = jedis.llen(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				length = jedisCluster.llen(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return length;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return length;
	}

	/**
	 * 自增
	*
	*@param key
	*@return
	*Long
	 */
	@SuppressWarnings("deprecation")
	public static Long incr(String key) {
		Long length = (long)0;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			Jedis jedis = pool.getResource();

			try {
				length = jedis.incr(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				length = jedisCluster.incr(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return length;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return length;
	}
	
	/**
	 * 自减
	*
	*@param key
	*@return
	*Long
	 */
	@SuppressWarnings("deprecation")
	public static Long decr(String key) {
		Long length = (long)0;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			Jedis jedis = pool.getResource();

			try {
				length = jedis.decr(key);
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				length = jedisCluster.decr(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return length;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return length;
	}

	/**
	 * 发布一个消息
	 * @param channel
	 * @param message
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Boolean publish(String channel, String message) {
		boolean result = false;
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = getJedisPool();
			if (pool == null) {
				return false;
			}

			Jedis jedis = pool.getResource();

			try {
				jedis.publish(channel, message);
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}

			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.publish(channel, message);
				result = true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				result = false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
		return result;
	}

	public static void isCheckUserIdnew(String userId, SysUser userType, String appId) {
		String date = DateUtil.getCurrentTime("yyyyMMddHHmmss");
//		if (CrhUser.UserType.operator.equals(userType)) {
//			/*if (getScoreById("loginBaseUserEmpId", userId) == null) {
//				addToRedisSetById("loginBaseUserEmpId", Double.valueOf(date), userId);
//			}*/
//			addToRedisSetById("loginBaseUserEmpId", Double.valueOf(date), userId);
//		} else if (CrhUser.UserType.client.equals(userType)) {
//			Map<String, String> onlineUser = new HashMap<String, String>();
//			onlineUser.put("user_id", userId);
//			onlineUser.put("app_id", appId);
//			String onlineUserInfo = FastJsonUtil.toJSONString(onlineUser);
//
//			/*if (getScoreById("loginUserId", onlineUserInfo) == null) {
//				RedisClientUtil.addToRedisSetById("loginUserId", Double.valueOf(date), onlineUserInfo);
//			}*/
//			RedisClientUtil.addToRedisSetById("loginUserId", Double.valueOf(date), onlineUserInfo);
//		}
	}

	@SuppressWarnings("deprecation")
	public static Boolean zrem(String key, String member) {
		if (StringUtils.isAnyBlank(key, member)) {
			return false;
		}
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				return false;
			}

			Jedis jedis = pool.getResource();
			try {
				String[] members = new String[] {member};
				Long result = jedis.zrem(key, members);
				if (result == 1) {
					return true;
				}
				return false;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}

			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				String[] members = new String[] {member};
				Long result = jedisCluster.zrem(key, members);
				if (result == 1) {
					return true;
				}
				return false;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static Boolean subscribe(final JedisPubSub pubSub, final String[] array) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				return false;
			}

			Jedis jedis = pool.getResource();
			try {
				jedis.subscribe(pubSub, array);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}

			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.subscribe(pubSub, array);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static Boolean psubscribe(final JedisPubSub pubSub, final String[] array) {
		Map<String, Object> configMap = parseConfigXml();
		String type = (String)configMap.get("type");
		if (StringUtils.isBlank(type) || "sentinels".equals(type)) {
			Pool<Jedis> pool = RedisClientUtil.getJedisPool();
			if (pool == null) {
				return false;
			}

			Jedis jedis = pool.getResource();
			try {
				jedis.psubscribe(pubSub, array);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				pool.returnResource(jedis);
			}
		} else {
			GenericObjectPool<JedisCluster> clusterPool = getClusterPool();
			if (clusterPool == null) {
				return false;
			}

			JedisCluster jedisCluster = null;
			try {
				jedisCluster = clusterPool.borrowObject();
				jedisCluster.psubscribe(pubSub, array);
				return true;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			} finally {
				clusterPool.returnObject(jedisCluster);
			}
		}
	}

		
}