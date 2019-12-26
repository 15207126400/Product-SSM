package com.yunwei.product.infobase.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.yunwei.common.util.RequestUtil;
import com.yunwei.context.jdbc.datasource.DBContextHolder;
import com.yunwei.context.jdbc.model.YwDbconfig;
import com.yunwei.context.jdbc.util.DataSourceUtil;
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
public class InfobaseDynamicDataSource extends AbstractDynamicDataSourceAop{
	private static Logger logger = Logger.getLogger(InfobaseDynamicDataSource.class);
	
	private static final String package_name = "com.yunwei.product.infobase.serivce.impl";
	
	/**
	 * 拦截service指定子类下的方法实现数据源动态切换
	 * @param joinPoint
	 * @throws Exception
	 */
	@Before(value="pointCut()")
    public void dynamicSetDataSoruce(JoinPoint joinPoint) throws Exception {
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
	  String wx_appid = getDynamicDataSourceAppId();
	  YwDbconfig ywDbconfig = ywDbconfigCache.getYwDbconfigByWxAppid(wx_appid,"1");
	  
	  // 切换数据源
	  DataSourceUtil.setMysqlDataResource(ywDbconfig);
	}

	@Override
	protected void oracleDynamicDataSource(String dbVersion) {
		String wx_appid = getDynamicDataSourceAppId();
		YwDbconfig ywDbconfig = ywDbconfigCache.getYwDbconfigByWxAppid(wx_appid,"2");
		  
		// 切换数据源
		DataSourceUtil.setOracleDataResource(ywDbconfig);
	}

	@Override
	protected void sqlserverDynamicDataSource(String dbVersion) {
		String wx_appid = getDynamicDataSourceAppId();
		YwDbconfig ywDbconfig = ywDbconfigCache.getYwDbconfigByWxAppid(wx_appid,"3");
		  
		// 切换数据源
		DataSourceUtil.setSqlserverDataResource(ywDbconfig);
	}
	
	/**
     * 获取动态数据源appId(默认为微信appId)
     * @return
     */
    private String getDynamicDataSourceAppId(){
    	HttpServletRequest request = RequestUtil.getRequest();
    	// 小程序切换数据库
		String wx_appid = request.getParameter("wx_appid");
		if(StringUtils.isBlank(wx_appid)){
			// 该wx_appid从拦截器里面获取
			wx_appid = (String) request.getAttribute("wx_appid");
		}
		return wx_appid;
    }

}
