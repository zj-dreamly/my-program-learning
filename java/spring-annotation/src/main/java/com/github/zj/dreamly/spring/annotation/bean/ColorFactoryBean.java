package com.github.zj.dreamly.spring.annotation.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * <h2>ColorFactoryBean</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 10:15
 **/
public class ColorFactoryBean implements FactoryBean<Color> {

	/**
	 * 返回一个Color对象，这个对象会添加到容器中
	 */
	@Override
	public Color getObject() {
		return new Color();
	}

	/**
	 * 是否为单例
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

	/**
	 * 返回Color对象类型
	 */
	@Override
	public Class<?> getObjectType() {
		return Color.class;
	}
}
