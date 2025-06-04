package com.example.application.domain.post.svc;

import com.example.application.domain.entity.Post;
import com.example.application.domain.post.dao.PostDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSVCImpl implements PostSVC {

  private final PostDAO postDAO;

  @Override
  public Long save(Post Post) {
    return postDAO.save(Post);
  }

  @Override
  public List<Post> findAll() {
    return postDAO.findAll();
  }

  @Override
  public Optional<Post> findById(Long id) {
    return postDAO.findById(id);
  }

  @Override
  public int updateById(Long id, Post Post) {
    return postDAO.updateById(id, Post);
  }

  @Override
  public int deleteByIds(List<Long> ids) {
    return postDAO.deleteByIds(ids);
  }

  @Override
  public int deleteById(Long postId) {
    return postDAO.deleteById(postId);
  }

  @Override
  public List<Post> findAllByPage(int page, int size) {
    int offset = (page - 1) * size;
    return postDAO.findAllByPage(offset, size);
  }

  @Override
  public PageResult<Post> findPostsByPage(int page, int size) {
    int offset = (page - 1) * size;
    List<Post> data = postDAO.findAllByPage(offset, size);
    int totalCount = postDAO.countAll();
    return new PageResult<>(data, totalCount, page, size);
  }
}
