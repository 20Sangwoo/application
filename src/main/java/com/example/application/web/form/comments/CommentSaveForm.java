package com.example.application.web.form.comments;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentSaveForm {
  private Long postId;

  @NotBlank(message = "내용은 필수 입력 항목입니다.")
  private String content;
}
