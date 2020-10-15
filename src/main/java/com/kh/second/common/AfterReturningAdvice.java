package com.kh.second.common;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kh.second.member.model.vo.Member;

@Repository //dao 에 적용하는
@Aspect //aop 를 의미함
public class AfterReturningAdvice {
	//로그인 메소드에 대한 로그 처리를 목적으로 하는 어드바이스로 만들어 봄
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Pointcut("execution(* com.kh.second.member.model.dao.MemberDao.selectLogin(..))")
	public void loginPointcut() {}
	
	//타겟 객체의 메소드가 실행 후 리턴될 때 작동되는 어드바이스
	@AfterReturning(pointcut="loginPointcut()", returning="returnObj")
	public void loginLogging(JoinPoint jp, Object returnObj) {
		//비즈니스 메소드가 리턴할 결과 데이터를 다른 용도로 처리할 때 사용할 수 있음
		if(returnObj instanceof Member) {
			Member member = (Member)returnObj;
			logger.info(new Date() + "$" + member.getUserid() + "$님이 접속하셨습니다.");
		}
	}
	
}
