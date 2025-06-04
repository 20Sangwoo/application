package com.example.application.web.form.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateForm {
  private Long postId;

  @NotBlank(message = "제목은 필수입니다")
  private String title;

  @NotBlank(message ="내용은 필수입니다")
  private String content;

  @NotBlank(message = "작성자는 필수입니다")
  private String author;

}