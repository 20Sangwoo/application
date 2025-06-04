package com.example.application.web.form.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveForm {
  @NotBlank(message = "제목은 필수 입력 항목입니다.")
  @Size(min = 3, max=50, message="제목은 최소3자 최대50자 이내로 입력해 주세요.")
  private String title;
  @NotBlank(message = "내용은 필수 입력 항목입니다.")
  private String content;

  private String writer;
}
