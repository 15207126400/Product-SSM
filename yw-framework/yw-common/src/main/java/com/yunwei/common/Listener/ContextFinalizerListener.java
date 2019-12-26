package com.yunwei.common.Listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;




import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import com.yunwei.common.cache.CacheManager;

/**
 * 监听数据库连接是否被关闭
* @ClassName: ContextFinalizerListener 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2019年1月9日 下午4:45:03 
*
 */
@WebListener
public class ContextFinalizerListener implements ServletContextListener{
	
	private final Logger logger = Logger.getLogger(ContextFinalizerListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                logger.info(String.format("ContextFinalizerListener:Driver %s deregistered", d));
            } catch (SQLException ex) {
            	logger.error(String.format("ContextFinalizerListener:Error deregistering driver %s", d) , ex);
            }
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } catch (InterruptedException e) {
        	logger.error("ContextFinalizerListener:SEVERE problem cleaning up: " + e.getMessage(),e);
        }
	}

}
