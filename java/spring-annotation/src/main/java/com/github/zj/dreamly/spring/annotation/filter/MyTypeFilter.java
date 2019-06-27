package com.github.zj.dreamly.spring.annotation.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * <h2>MyTypeFilter</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-27 18:23
 * <p>
 * metadataReader：读取到的当前正在扫描的类的信息
 * metadataReaderFactory：可以获取到其他任何类信息的
 **/
@Slf4j
public class MyTypeFilter implements TypeFilter {

	public static final String ER = "er";

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {

		//获取当前类注解的信息
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		//获取当前正在扫描的类的类信息
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		//获取当前类资源（类的路径）
		Resource resource = metadataReader.getResource();
		String className = classMetadata.getClassName();
		log.info("--->" + className);
		return className.contains(ER);
	}
}
