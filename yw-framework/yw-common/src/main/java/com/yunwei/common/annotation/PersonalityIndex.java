package com.yunwei.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 个性化首页注解
* @ClassName: PersonalityIndex 
* @Description: TODO(TODO) 
* @author zhangjh
* @date 2018年11月23日 下午2:44:33 
*
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonalityIndex {

}
