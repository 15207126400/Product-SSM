package com.yunwei.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* @ClassName: Base64Decode 
* @Description: TODO(TODO) 是否需要base64解码
* @author zhangjh
* @date 2018年4月13日 下午12:40:57 
*
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64Decode {

}
