package com.yunwei.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库配置
* @ClassName: DefalutDataSource 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年6月19日 下午6:23:50 
*
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

	/**
	 * 数据库配置名称，默认mysql(oracle,sqlserver)
	 * @return
	 */
	public String name() default "mysql";
	
	/**
	 * 数据库版本（例如sqlserver[T+,或者T3版本]）
	 * @return
	 */
	public String version() default " ";
}
