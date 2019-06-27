package com.github.zj.dreamly.design.pattern.pattern.behavioral.visitor;

/**
 * @author 苍海之南
 */
public interface IVisitor {

	void visit(FreeCourse freeCourse);

	void visit(CodingCourse codingCourse);

}
