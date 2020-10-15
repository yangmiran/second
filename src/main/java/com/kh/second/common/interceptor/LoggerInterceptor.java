package com.kh.second.common.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 인터셉터는 디스패쳐서블릿과 컨트롤러 사이에서 구동되는 클래스임
// log4j.xml에서 debug레벨로 로그를 남기도록 설정되어 있음
// LoggingEvent로 발생되는 시간을 절약할 수 있음
public class LoggerInterceptor extends HandlerInterceptorAdapter {
   // org.slf4j.Logger 타입의 logger를 생성함
   // LoggerFactory.getLogger 메소드의 매개변수로 현재 클래스 객체를 전달함
   private Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

   // 이벤트 발생 직전
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
         throws Exception {
      // DispatcherServlet에서 Controller가기 전에 구동되는 메소드
      // debug 레벨일 때 로그 처리하도록 함
      // debug 상태면?

      if (logger.isDebugEnabled()) {
         logger.debug("=============START===============");
         // 찾아가는 대상의 정보
         logger.debug(request.getRequestURI());
         logger.debug("-------------------------------------------------------");
      }
      // 항상 true가 리턴되게 해야 함
      return super.preHandle(request, response, handler);
   }

   // 이벤트가 발생하였을 때
   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
         ModelAndView modelAndView) throws Exception {
      // Controller에서 리턴되어 뷰리졸버로 가기 전에 구동되는 메소드
      super.postHandle(request, response, handler, modelAndView);

      if (logger.isDebugEnabled()) {
         logger.debug("===============view===============");

      }
   }

   // 이벤트의 구동이 완료되면
   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
         throws Exception {
      // 뷰리졸버가 뷰를 실행해서 내보내고 나면 구동되는 메소드
      if (logger.isDebugEnabled()) {
         logger.debug("================END===============");
      }
      super.afterCompletion(request, response, handler, ex);
   }

}