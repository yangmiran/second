package com.kh.second.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutCommon {
	//포인트컷만 따로 작성해 둘 수도 있음
	
	@Pointcut("execution(* com.kh.second..service..*Impl.*(..))")
	public void serviceAllPointcut() {}
	
	@Pointcut("execution(* com.kh.second..service..*Impl.select*(..))")
	public void getPointcut() {}
	
	@Pointcut("execution(* com.kh.second..service..*Impl.insert*(..))")
	public void setPointcut() {}
}
