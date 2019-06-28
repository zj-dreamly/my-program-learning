package com.github.zj.dreamly.spring.annotation.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <h2>WindowsCondition</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 08:34
 **/
public class WindowsCondition implements Condition {
	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		Environment environment = conditionContext.getEnvironment();
		String property = environment.getProperty("os.name");
		assert property != null;
		return property.contains("Windows");
	}
}
