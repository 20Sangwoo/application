package com.example.application.domain.entity;

import lombok.Data;

@Data
public class Member {
  private long memberNo;    //회원번호
  private String nickname;  //별칭
  private String password;  //비밀번호
  private String email;     //이메일
  private String tel;       //전화번호
}
