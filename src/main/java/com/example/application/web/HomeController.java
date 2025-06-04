package com.example.application.web;

import com.example.application.web.form.login.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

  @GetMapping("/")
  public String home(HttpServletRequest request) {
    String view = null;

    HttpSession session = request.getSession(false);
    //로그인 전
    if (session == null || session.getAttribute("loginMember") == null) {
      view = "index";

    } else {
      //로그인 후
      LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
      log.info("로그인 사용자: {}", loginMember.getNickname());
      view = "index";
    }
    return view;
  }
}
