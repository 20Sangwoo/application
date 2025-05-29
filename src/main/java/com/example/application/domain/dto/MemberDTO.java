package com.example.application.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
  private String nickname;  //별칭
  private String password;  //비밀번호
  private String email;     //이메일
  private String tel;       //전화번호
}
