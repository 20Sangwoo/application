package com.example.application.domain.member.dao;

import com.example.application.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO{

  private final NamedParameterJdbcTemplate template;

  @Override
  public Member insertMember(Member member) {
    // 1) sql 준비
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member (mem_no,nickname,password,email,tel) ");
    sql.append("values(member_mem_no_seq.nextval, :nickname,:password,:email,:tel) ");

    // 2) sql 실행
    SqlParameterSource param = new BeanPropertySqlParameterSource(member);
    //KeyHolder 의 역할 : insert쿼리를 실행한 후 데이터베이스에서 자동 생성된 키(pk,시퀀스)만을 반환 하는데 사용
    KeyHolder keyHolder = new GeneratedKeyHolder();
    int rows = template.update(sql.toString(), param, keyHolder, new String[]{"mem_no"});
    long memberNo = ((Number) keyHolder.getKeys().get("mem_no")).longValue();

    return findByMemberNo(memberNo).get();
  }

  @Override
  public boolean isExist(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select count(*) ");
    sql.append("  from member ");
    sql.append(" where email = :email ");

    Map<String,String> param = Map.of("email",email);
    Integer cntOfRec = template.queryForObject(sql.toString(), param, Integer.class);

    return (cntOfRec == 1) ? true : false;
  }

  @Override
  public Optional<Member> findByMemberNo(Long memberNo) {
    StringBuffer sql = new StringBuffer();
    sql.append("select  mem_no, ");
    sql.append("        nickname, ");
    sql.append("        passwd, ");
    sql.append("        email, ");
    sql.append("        tel, ");
    sql.append(" from member ");
    sql.append("where mem_no = :memberNo ");

    Map<String,Long> param = Map.of("memberNo",memberNo);
    try {
      Member member = template.queryForObject(
          sql.toString(), param, BeanPropertyRowMapper.newInstance(Member.class));

      return Optional.of(member);

    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();

    }
  }

  @Override
  public Optional<Member> findByEmail(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select  mem_no, ");
    sql.append("        nickname, ");
    sql.append("        password, ");
    sql.append("        email, ");
    sql.append("        tel, ");
    sql.append(" from member ");
    sql.append("where email = :email ");

    Map<String,String> param = Map.of("email",email);
    try {
      Member member = template.queryForObject(
          sql.toString(), param,BeanPropertyRowMapper.newInstance(Member.class));

      return Optional.of(member);

    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();

    }
  }


}
