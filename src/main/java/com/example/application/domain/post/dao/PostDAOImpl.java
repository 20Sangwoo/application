package com.example.application.domain.post.dao;

import com.example.application.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Repository
public class PostDAOImpl implements PostDAO {

  private final NamedParameterJdbcTemplate template;

  RowMapper<Post> postRowMapper() {
    return (rs, rowNum) -> {
      Post post = new Post();
      post.setPostId(rs.getLong("post_id"));
      post.setTitle(rs.getString("title"));
      post.setContent(rs.getString("content"));
      post.setAuthor(rs.getString("author"));
      post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
      post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
      return post;
    };
  }

  /**
   * Post등록
   *
   * @param Post
   * @return
   */
  @Override
  public Long save(Post Post) {
    StringBuffer sql = new StringBuffer();
    sql.append("INSERT INTO post (post_id, title, content, author, created_at, updated_at) ");
    sql.append("     VALUES (bbs_POST_ID_SEQ.nextval, :title, :content, :author, :createdAt, :updatedAt)");


    SqlParameterSource param = new BeanPropertySqlParameterSource(Post);

    KeyHolder keyHolder = new GeneratedKeyHolder();

    long rows = template.update(sql.toString(), param, keyHolder, new String[]{"post_id"});
    log.info("rows={}", rows);

    // keyHolder에서 post_id 추출
    Number pidNumber = (Number) keyHolder.getKeys().get("post_id");
    long pid = pidNumber.longValue();

    return pid;
  }


  @Override
  public List<Post> findAll() {
    String sql = "SELECT * FROM post ORDER BY created_at DESC";
    return template.query(sql, postRowMapper());
  }

  @Override
  public Optional<Post> findById(Long postId) {
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT post_id, title, content, author, created_at, updated_at ");
    sql.append("  FROM post ");
    sql.append(" WHERE post_id = :postId ");

    SqlParameterSource param = new MapSqlParameterSource().addValue("postId", postId);

    Post Post = null;
    try {
      Post = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Post.class));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }

    return Optional.of(Post);
  }


  /**
   * 게시글 수정
   *
   * @param postId 게시글 번호
   * @param Post   게시글 정보
   * @return 수정된 게시글 건수
   */
  @Override
  public int updateById(Long postId, Post Post) {
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE post ");
    sql.append("   SET title = :title, content = :content, author = :author, ");
    sql.append("       updated_at = :updatedAt ");
    sql.append(" WHERE post_id = :postId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title", Post.getTitle())
        .addValue("content", Post.getContent())
        .addValue("author", Post.getAuthor())
        .addValue("updatedAt", Post.getUpdatedAt())
        .addValue("postId", postId);

    int rows = template.update(sql.toString(), param);

    return rows;
  }

  //삭제(단건)
  @Override
  public int deleteById(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM post ");
    sql.append(" WHERE post_id = :id ");

    Map<String, Long> param = Map.of("id", id);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  //삭제 (여러건)
  @Override
  public int deleteByIds(List<Long> ids) {
    StringBuffer sql = new StringBuffer();
    sql.append("DELETE FROM post ");
    sql.append(" WHERE post_id IN ( :ids ) ");

    Map<String, List<Long>> param = Map.of("ids", ids);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  @Override
  public List<Post> findAllByPage(int offset, int limit) {
    String sql = "SELECT * FROM ( " +
        "  SELECT a.*, ROWNUM rnum FROM ( " +
        "    SELECT * FROM post ORDER BY created_at DESC " +
        "  ) a WHERE ROWNUM <= :endRow " +
        ") WHERE rnum >= :startRow";

    int startRow = offset + 1;  // 1부터 시작하도록 변경
    int endRow = offset + limit;

    MapSqlParameterSource param = new MapSqlParameterSource();
    param.addValue("startRow", startRow);
    param.addValue("endRow", endRow);

    return template.query(sql, param, postRowMapper());
  }


  @Override
  public int countAll() {
    String sql = "SELECT COUNT(*) FROM post";
    return template.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
  }
}