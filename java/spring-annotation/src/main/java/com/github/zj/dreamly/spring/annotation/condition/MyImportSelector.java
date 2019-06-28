package com.github.zj.dreamly.spring.annotation.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <h2>MyImportSelector</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-28 09:37
 **/
public class MyImportSelector implements ImportSelector {
	@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		return new String[]{"com.github.zj.dreamly.spring.annotation.bean.Blue",
		"com.github.zj.dreamly.spring.annotation.bean.Yellow"};
	}
}
