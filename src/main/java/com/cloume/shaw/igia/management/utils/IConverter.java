package com.cloume.shaw.igia.management.utils;

/**
 * 将一个对象类型转换为另一个类型
 * @author Gang
 *
 */
public interface IConverter {
	/**
	 * @param name 待转换属性名
	 * @param value 待转换的属性值（实例）
	 * @return 转换之后的值（实例）
	 */
	public Object convert(String name, Object value);
}
