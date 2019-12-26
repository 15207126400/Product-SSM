package com.yunwei.context.jdbc.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yunwei.context.jdbc.datasource.DBContextHolder;
import com.yunwei.context.jdbc.model.YwDbconfig;


/**
 * 数据源工具类
 * @author Administrator
 *
 */
public class DataSourceUtil {
	/**
	 * 配置mysql数据源
	 */
	public static void setMysqlDataResource(YwDbconfig dbconfig){
		if(null != dbconfig){
			//map.put(DBContextHolder.DATASOURCE_URL,"jdbc:mysql://111.230.177.61:3306/yw_system?useUnicode=true&amp;characterEncoding=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(DBContextHolder.DATASOURCE_KEY, dbconfig.getUser_id()+"-"+dbconfig.getDb_type()+"-"+dbconfig.getDb_function_type());
			map.put(DBContextHolder.DATASOURCE_DRIVER, dbconfig.getDb_driver());
			StringBuffer buffer = new StringBuffer();
			buffer.append("jdbc:mysql://")
				  .append(dbconfig.getDb_protocol())
				  .append(":")
				  .append(dbconfig.getDb_port())
				  .append("/")
				  .append(dbconfig.getDb_name())
				  .append("?useUnicode=true&amp;characterEncoding=utf-8");
			map.put(DBContextHolder.DATASOURCE_URL,buffer.toString());
			map.put(DBContextHolder.DATASOURCE_USERNAME, dbconfig.getDb_username());
			map.put(DBContextHolder.DATASOURCE_PASSWORD, dbconfig.getDb_password());
			DBContextHolder.setDBType(map);
		}
	}	
		
	/**
	 * 配置Oracle数据源
	 */
	public static void setOracleDataResource(YwDbconfig dbconfig){
		if(null != dbconfig){
			//map.put(DBContextHolder.DATASOURCE_URL,"jdbc:mysql://111.230.177.61:3306/yw_system?useUnicode=true&amp;characterEncoding=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(DBContextHolder.DATASOURCE_KEY, dbconfig.getUser_id()+"-"+dbconfig.getDb_type()+"-"+dbconfig.getDb_function_type());
			map.put(DBContextHolder.DATASOURCE_DRIVER, dbconfig.getDb_driver());
			StringBuffer buffer = new StringBuffer();
			buffer.append("jdbc:oracle:thin:@")
				  .append(dbconfig.getDb_protocol())
			      .append(":")
			      .append(dbconfig.getDb_port())
                  .append(":")
                  .append(dbconfig.getDb_name());
			map.put(DBContextHolder.DATASOURCE_URL,buffer.toString());
			map.put(DBContextHolder.DATASOURCE_USERNAME, dbconfig.getDb_username());
			map.put(DBContextHolder.DATASOURCE_PASSWORD, dbconfig.getDb_password());
			DBContextHolder.setDBType(map);
		}
	}		
	/**
	 * 配置sqlserver数据源
	 */
	public static void setSqlserverDataResource(YwDbconfig dbconfig){
		if(null != dbconfig){
			//map.put(DBContextHolder.DATASOURCE_URL,"jdbc:mysql://111.230.177.61:3306/yw_system?useUnicode=true&amp;characterEncoding=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(DBContextHolder.DATASOURCE_KEY, dbconfig.getUser_id()+"-"+dbconfig.getDb_type()+"-"+dbconfig.getDb_function_type());
			map.put(DBContextHolder.DATASOURCE_DRIVER, dbconfig.getDb_driver());
			StringBuffer buffer = new StringBuffer();
			// sqlserver数据库
			buffer.append("jdbc:sqlserver://")
				  .append(dbconfig.getDb_protocol())
				  .append(":")
				  .append(dbconfig.getDb_port())
				  .append(";")
				  .append("databaseName=")
				  .append(dbconfig.getDb_name());
			map.put(DBContextHolder.DATASOURCE_URL,buffer.toString());
			map.put(DBContextHolder.DATASOURCE_USERNAME, dbconfig.getDb_username());
			map.put(DBContextHolder.DATASOURCE_PASSWORD, dbconfig.getDb_password());
			DBContextHolder.setDBType(map);
		}
	}
}
