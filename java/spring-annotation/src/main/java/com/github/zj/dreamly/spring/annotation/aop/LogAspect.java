package com.github.zj.dreamly.spring.annotation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * <h2>LogAspect</h2>
 *
 * @author: 苍海之南
 * @since: 2019-06-30 09:50
 **/
@Aspect
public class LogAspect {

	/**
	 * 抽取公共的切入点表达式
	 * 1、本类引用
	 * 2、其他的切面引用
	 */
	@Pointcut("execution(public int com.github.zj.dreamly.spring.annotation.aop.MathCalculator.*(..))")
	public void pointCut() {
	}

	/**
	 * 在目标方法之前切入；切入点表达式（指定在哪个方法切入）
	 */
	@Before("pointCut()")
	public void logStart(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		System.out.println("" + joinPoint.getSignature().getName() + "：run。。。@Before:params：{" + Arrays.asList(args) + "}");
	}

	@After("com.github.zj.dreamly.spring.annotation.aop.LogAspect.pointCut()")
	public void logEnd(JoinPoint joinPoint) {
		System.out.println("" + joinPoint.getSignature().getName() + "end。。。@After");
	}

	/**
	 * JoinPoint一定要出现在参数表的第一位
	 */
	@AfterReturning(value = "pointCut()", returning = "result")
	public void logReturn(JoinPoint joinPoint, Object result) {
		System.out.println("" + joinPoint.getSignature().getName() + "normal return。。。@AfterReturning:result：{" + result + "}");
	}

	@AfterThrowing(value = "pointCut()", throwing = "exception")
	public void logException(JoinPoint joinPoint, Exception exception) {
		System.out.println("" + joinPoint.getSignature().getName() + "exception。。。exception：{" + exception + "}");
	}

}
