package com.example.application.domain.comments.svc;

import com.example.application.domain.comments.dao.CommentsDAO;
import com.example.application.domain.entity.Comments;
import com.example.application.domain.post.svc.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsSVCImpl implements CommentsSVC {

  private final CommentsDAO commentsDAO;

  @Override
  public Long save(Comments comment) {
    return commentsDAO.save(comment);
  }

  @Override
  public List<Comments> findAllByPostId(Long postId) {
    return commentsDAO.findAllByPostId(postId);
  }

  @Override
  public Optional<Comments> findById(Long id) {
    return commentsDAO.findById(id);
  }

  @Override
  public int updateById(Long id, Comments comment) {
    return commentsDAO.updateById(id, comment);
  }

  @Override
  public int deleteById(Long id) {
    return commentsDAO.deleteById(id);
  }

  @Override
  public int deleteByPostId(Long postId) {
    return commentsDAO.deleteByPostId(postId);
  }

  @Override
  public List<Comments> findByPostIdByPage(Long postId, int page, int size) {
    int offset = (page - 1) * size;
    return commentsDAO.findByPostIdByPage(postId, offset, size);
  }

  @Override
  public int countByPostId(Long postId) {
    return commentsDAO.countByPostId(postId);
  }

  @Override
  public PageResult<Comments> findCommentsByPostIdByPage(Long postId, int page, int size) {
    int offset = (page - 1) * size;
    List<Comments> data = commentsDAO.findByPostIdByPage(postId, offset, size);
    int totalCount = commentsDAO.countByPostId(postId);
    return new PageResult<>(data, totalCount, page, size);
  }
  }
