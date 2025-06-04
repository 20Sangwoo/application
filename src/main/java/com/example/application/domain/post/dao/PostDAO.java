package com.example.application.domain.post.dao;

import com.example.application.domain.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
  Long save(Post Post);
  List<Post> findAll();
  Optional<Post> findById(Long postId);
  int updateById(Long postId, Post Post);
  int deleteById(Long postId);

  int deleteByIds(List<Long> ids);
  int countAll();
  List<Post> findAllByPage(int offset, int size);
}
