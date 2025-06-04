package com.example.application.domain.post.svc;

import com.example.application.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostSVC {
  //post 등록
  Long save(Post Post);

  //post 조회(여러건)
  List<Post> findAll();

  //post 조회(단건)
  Optional<Post> findById(Long id);

  //post 수정
  int updateById(Long id, Post Post);

  //post 삭제(여러건)
  int deleteByIds(List<Long> ids);

  //post 삭제(단건)
  int deleteById(Long postId);

  List<Post> findAllByPage(int page, int size);

  PageResult<Post> findPostsByPage(int page, int size);
}