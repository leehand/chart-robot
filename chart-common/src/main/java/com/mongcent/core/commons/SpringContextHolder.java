package com.mongcent.core.commons;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 * 
 * @author Created by Quant on 2017/04/02 22:56:41
 */
@Component("springContextHolder")
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	// 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
    @Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	// 取得存储在静态变量中的ApplicationContext.
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	// 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	// 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	// 如果有多个Bean符合Class, 取出第一个.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		@SuppressWarnings("rawtypes")
		Map beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			return (T) beanMaps.values().iterator().next();
		} else {
			return null;
		}
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

}