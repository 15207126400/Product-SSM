package com.yunwei.product.backend.aop;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yunwei.common.annotation.DataSource;
import com.yunwei.common.exception.BizException;
import com.yunwei.common.util.RequestUtil;
import com.yunwei.context.jdbc.datasource.DBContextHolder;
import com.yunwei.context.jdbc.model.YwDbconfig;
import com.yunwei.context.jdbc.util.DataSourceUtil;
import com.yunwei.context.sys.cache.YwDictionaryCache;
import com.yunwei.context.sys.model.YwDictionary;
import com.yunwei.context.sys.usercenter.model.YwUser;
import com.yunwei.product.common.aop.AbstractDynamicDataSourceAop;

/**
 * service层面aop，根据不同业务用户来切换数据源(主要用于业务数据库的切换)
* @ClassName: DynaDataSourceInterceptor 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月19日 下午3:21:47 
*
 */
@Component
@Aspect
@Order(0)// 加入aop执行顺序，使数据源切换在数据源事务之前
public class BackendDynamicDataSource extends AbstractDynamicDataSourceAop {
	private static Logger logger = Logger.getLogger(BackendDynamicDataSource.class);
	
	private static final String package_name = "com.yunwei.product.backend.service.impl";
	private static final String mysql = "1";
	private static final String oracle = "2";
	private static final String sqlserver = "3";
	private static final String db_version_type_dic_key="1079";// 数据库版本类型字典项值
	
	@Autowired
	private YwDictionaryCache ywDictionaryCache;
//	
//	@Pointcut("execution(* com.yunwei.product.backend.sqlserver..*Service(..))")
//	public void pointCut2(){};
	
	/**
	 * 拦截service指定子类下的方法实现数据源动态切换
	 * @param joinPoint
	 * @throws Exception
	 */
	@Before(value="pointCut()")
    public void setDataSoruce(JoinPoint joinPoint) throws Exception {
		// 拦截指定的包名
		String packageName = joinPoint.getTarget().getClass().getPackage().toString();
		if(StringUtils.contains(packageName, package_name)){
			super.dynamicSetDataSoruce(joinPoint);
		}
        
	}
	
	/**
	 * service方法执行完成后，删除该数据源配置
	 */
	@After(value="pointCut()")
    public void clearDataSource(JoinPoint joinPoint) {
		String packageName = joinPoint.getTarget().getClass().getPackage().toString();
		if(StringUtils.contains(packageName, package_name)){
			logger.info("动态数据源关闭");
			DBContextHolder.clearDBType();
		}
    }

	@Override
	protected void mysqlDynamicDataSource(String dbVersion) {
		YwDbconfig dbconfig = this.getDynamicDataSource(dbVersion,mysql);
		if(dbconfig == null){
			throw new BizException("mysql数据库版本["+ dbVersion +"]不存在,请重新配置");
		}
		// 切换数据源
		DataSourceUtil.setMysqlDataResource(dbconfig);
	}
	
	@Override
	protected void oracleDynamicDataSource(String dbVersion) {
		YwDbconfig dbconfig = this.getDynamicDataSource(dbVersion,oracle);
		if(dbconfig == null){
			throw new BizException("oracle数据库版本["+ dbVersion +"]不存在,请重新配置");
		}
		// 切换数据源
		DataSourceUtil.setOracleDataResource(dbconfig);
	}

	@Override
	protected void sqlserverDynamicDataSource(String dbVersion) {
		YwDbconfig dbconfig = this.getDynamicDataSource(dbVersion,sqlserver);
		if(dbconfig == null){
			throw new BizException("sqlserver数据库版本["+ dbVersion +"]不存在,请重新配置");
		}
		// 切换数据源
		DataSourceUtil.setSqlserverDataResource(dbconfig);
	}
	
	/**
	 * 设置动态数据源
	 * @param dbVersion
	 * @param db_type
	 */
	private YwDbconfig getDynamicDataSource(String dbVersion,String db_type){
		YwUser ywUser = getDynamicDataSourceUser();
		String dbVersionSubkey = getDbVersionSubkey(dbVersion);
		YwDbconfig ywDbconfig = null;
		String user_id = ywUser.getUser_id();
		if(StringUtils.isNotEmpty(dbVersionSubkey)){
			ywDbconfig = ywDbconfigCache.getYwDbconfig(ywUser.getUser_id(),db_type,dbVersionSubkey);
		} else {
			ywDbconfig = ywDbconfigCache.getYwDbconfig(user_id, db_type);
		}
		return ywDbconfig;
	}
	
	private String getDbVersionSubkey(String dbVersion){
		YwDictionary dictionary = ywDictionaryCache.getDictionaryBySubvalue(db_version_type_dic_key, dbVersion);
	    if(dictionary != null){
	    	return dictionary.getDic_subkey();
	    }
	    return "";
	}
	
	/**
     * 获取动态数据源用户
     * @return
     */
    private YwUser getDynamicDataSourceUser(){
    	// 直接从请求中获取用户
    	Object user = null;
    	HttpServletRequest request = RequestUtil.getRequest();
    	if(request != null){
    		user = request.getSession().getAttribute("user");
    	}
    	if(user == null){
    		// 间接从ServletContext容器中获取用户[主要用于不是请求过程中的获取，例如：定时器获取]
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
	    	ServletContext servletContext = webApplicationContext.getServletContext();
	    	user =servletContext.getAttribute("user");
    	}
    	YwUser ywUser = (YwUser) user != null ? (YwUser) user : new YwUser();
    	return ywUser;
    }

	
}
