package com.kh.second.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class BeforeAdviceOutOfPointcut {
	//따로 작성해 놓은 포인트컷을 사용하기 위한 어드바이스임.
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Before("PointcutCommon.serviceAllPointcut()")
	public void beforeConsole(JoinPoint jp) {
		//모든 Service 구현 클래스의 모든 메소드 구동 직전에 실행되는 어드바이스임
		String methodName = jp.getSignature().getName();
		Object[] args = jp.getArgs();
		
		logger.info("[사전처리] : " + methodName + "() 메소드 첫번째 전달인자 정보 : " + ((args.length > 0)? args[0].toString() : 0));
	}
}
