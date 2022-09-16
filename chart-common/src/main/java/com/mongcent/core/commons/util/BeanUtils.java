package com.mongcent.core.commons.util;

import java.util.List;
import java.util.Set;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * 封装Orika , 实现深度转换Bean<->Bean的Util.实现:
 *
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	/**
	 * 创建单例，避免消耗资源
	 */
	private static MapperFactory mapperFactory;
	private static MapperFacade mapper;

	public static MapperFacade getMapper() {
		if (mapper == null) {
			mapperFactory = new DefaultMapperFactory.Builder().build();
			mapper = mapperFactory.getMapperFacade();
		}
		return mapper;
	}

	/**
	 * 转换对象的类型. source-->destinationClass
	 *
	 * @param source
	 *            源对象
	 * @param destinationClass
	 *            目标类
	 * @return T
	 * @since 1.0
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		return getMapper().map(source, destinationClass);
	}

	/**
	 * 转换List中对象的类型.sourceList--->destinationClass
	 *
	 * @param sourceList
	 *            源 List
	 * @param destinationClass
	 *            目标类
	 * @return List<T>
	 */
	public static <T> List<T> mapList(List<?> sourceList, Class<T> destinationClass) {
		return getMapper().mapAsList(sourceList, destinationClass);
	}

	/**
	 * 转换Set中对象的类型.sourceSet--->destinationClass
	 *
	 * @param sourceSet
	 *            源 Set<T>
	 * @param destinationClass
	 *            目标类
	 * @return Set<T>
	 */
	public static <T> Set<T> mapSet(Set<?> sourceSet, Class<T> destinationClass) {
		return getMapper().mapAsSet(sourceSet, destinationClass);
	}

	/**
	 * 转换Array中对象的类型
	 *
	 * @param sourceList
	 *            源 T[]
	 * @param destinationClass
	 *            目标类
	 * @return T[]
	 */
	public static <T> T[] mapArray(T[] destination, T[] sourceList, Class<T> destinationClass) {
		return getMapper().mapAsArray(destination, sourceList, destinationClass);
	}

	/**
	 * 将对象A的值拷贝到对象B中.source--->destinationObject
	 *
	 * @param source
	 *            源对象
	 * @param destinationObject
	 *            目标对象
	 * @since 1.0
	 */
	public static void copy(Object source, Object destinationObject) {
		mapper.map(source, destinationObject);
	}

}
