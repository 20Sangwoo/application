package com.example.application.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component   // 스프링컨테이너에 빈(스프링컨테이너에서 관리하는 객체) 으로 등록
public class LoginCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //리다이렉트 Url
    String redirectUrl = null;

    log.info("handler={}", handler.getClass());
    //요청 URI

    String requestURI = request.getRequestURI();

    //세션조회
    HttpSession session = request.getSession(false);

    // 로그인전 : 세션이 없거나 loginMember정보가 없는경우 로그인 화면으로 리다이렉트
    if(session == null || session.getAttribute("loginMember") == null){
      response.sendRedirect("/login");   // 302 GET http://localhost:9080/login
    }

    return true;
  }
}
