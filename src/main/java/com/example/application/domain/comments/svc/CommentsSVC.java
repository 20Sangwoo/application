package com.example.application.domain.comments.svc;

import com.example.application.domain.entity.Comments;
import com.example.application.domain.post.svc.PageResult;

import java.util.List;
import java.util.Optional;

public interface CommentsSVC {

  Long save(Comments comment);

  List<Comments> findAllByPostId(Long postId);

  Optional<Comments> findById(Long id);

  int updateById(Long id, Comments comment);

  int deleteById(Long id);

  int deleteByPostId(Long postId);
  // 기존
  List<Comments> findByPostIdByPage(Long postId, int page, int size);

  int countByPostId(Long postId);

  PageResult<Comments> findCommentsByPostIdByPage(Long postId, int page, int size);

}

