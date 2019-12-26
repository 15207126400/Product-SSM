package com.yunwei.product.common.aop;


import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import com.yunwei.common.annotation.DataSource;
import com.yunwei.context.jdbc.cache.YwDbconfigCache;

/**
 * service层面aop，根据不同业务用户来切换数据源(主要用于业务数据库的切换)
* @ClassName: DynaDataSourceInterceptor 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月19日 下午3:21:47 
*
 */
public abstract class AbstractDynamicDataSourceAop {
	private static Logger logger = Logger.getLogger(AbstractDynamicDataSourceAop.class);
	private static final String mysql = "mysql";
	private static final String oracle = "oracle";
	private static final String sqlserver = "sqlserver";
	
	@Autowired
	protected YwDbconfigCache ywDbconfigCache;
	
	/**
	 * 拦截父类service以及子类下的所有方法
	 */
	@Pointcut("within(com.yunwei.common.base.serivce.impl.IBaseServiceImpl+)")
    public void pointCut(){};
	
	/**
	 * 拦截service方法实现数据源动态切换
	 * @param joinPoint
	 * @throws Exception
	 */
    protected void dynamicSetDataSoruce(JoinPoint joinPoint) throws Exception {
		// 获取拦截的方法上的datasource注解
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		DataSource dataSource = method.getAnnotation(DataSource.class);
		
		// 判断datasource注解是否存在【不存在默认mysql数据源】
		if(dataSource != null){
			logger.info("dataSource:"+dataSource.name());
			String dbValue = dataSource.name();
			String dbVersion = dataSource.version();
			if(StringUtils.equals(dbValue, mysql)){
				// mysql数据源切换
				mysqlDynamicDataSource(dbVersion.trim());
			} else if(StringUtils.equals(dbValue, oracle)){
				// oracle数据源切换
				oracleDynamicDataSource(dbVersion.trim());
			} else if(StringUtils.equals(dbValue, sqlserver)){
				// sqlserver数据源切换
				sqlserverDynamicDataSource(dbVersion.trim());
			}
		} else {
			mysqlDynamicDataSource("");
		}
			
        
	}
    
	/**
	 * mysql动态数据源
	 */
	protected abstract void mysqlDynamicDataSource(String dbVersion);
	
	/**
	 * oracle动态数据源
	 */
	protected abstract void oracleDynamicDataSource(String dbVersion);
    
    /**
     * sqlserver动态数据源
     */
	protected abstract void sqlserverDynamicDataSource(String dbVersion);
    
	
}
