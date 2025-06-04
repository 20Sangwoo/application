package com.example.application.web;

import com.example.application.domain.comments.svc.CommentsSVC;
import com.example.application.domain.entity.Comments;
import com.example.application.domain.entity.Member;
import com.example.application.domain.entity.Post;
import com.example.application.domain.post.svc.PageResult;
import com.example.application.domain.post.svc.PostSVC;
import com.example.application.web.form.login.LoginMember;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

  private final PostSVC postSVC;
  private final CommentsSVC commentsSVC;


  // 기본 페이지
  @GetMapping("/")
  public String index() {
    return "index";
  }


  @GetMapping("/list")
  public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                     @RequestParam(name = "size", defaultValue = "10") int size,
                     Model model) {
    PageResult<Post> pageResult = postSVC.findPostsByPage(page, size);
    model.addAttribute("pageResult", pageResult);

    return "post/list";
  }

  // 등록 화면
  @GetMapping("/add")
  public String addForm(Model model, @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
    Post post = new Post();
    if (loginMember != null) {
      post.setAuthor(loginMember.getNickname());  // 작성자 자동 세팅
    }
    model.addAttribute("post", post);
    return "post/add";
  }

  // 등록 처리
  @PostMapping("/add")
  public String add(
      @jakarta.validation.Valid @ModelAttribute("post") Post post,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    log.info("post={}", post);

    // 1) 유효성 검사
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "post/add";
    }

    post.setCreatedAt(LocalDateTime.now());
    post.setUpdatedAt(LocalDateTime.now());
    Long postId = postSVC.save(post);
    redirectAttributes.addAttribute("id", postId);
    return "redirect:/post/list";
  }

  // 조회(단건)
  @GetMapping("/{id}")
  public String findById(
      @PathVariable("id") Long id,
      @RequestParam(name = "page", defaultValue = "1") int page,
      @RequestParam(name = "size", defaultValue = "5") int size,
      Model model,
      HttpSession session) {

    Post post = postSVC.findById(id).orElseThrow();
    model.addAttribute("post", post);

    List<Comments> comments = commentsSVC.findByPostIdByPage(id, page, size);
    int totalCount = commentsSVC.countByPostId(id);
    int totalPages = (int) Math.ceil((double) totalCount / size);

    model.addAttribute("comments", comments);
    model.addAttribute("page", page);
    model.addAttribute("size", size);
    model.addAttribute("totalPages", totalPages);

    Comments newComment = new Comments();
    newComment.setPostId(id);
    model.addAttribute("newComment", newComment);

    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    model.addAttribute("loginMember", loginMember);

    return "post/detail";
  }



  // 수정 화면
  @GetMapping("/{id}/edit")
  public String updateForm(
      @PathVariable("id") Long id, Model model) {
    Post post = postSVC.findById(id).orElseThrow();
    model.addAttribute("post", post);
    return "post/update";
  }

  // 수정 처리
  @PostMapping("/{id}/update")
  public String update(
      @PathVariable("id") Long id,
      @Valid @ModelAttribute("post") Post post,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    log.info("updatePost={}", post);

    // 유효성 검사
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "post/update";
    }

    post.setUpdatedAt(LocalDateTime.now());
    postSVC.updateById(id, post);

    redirectAttributes.addAttribute("id", id);

    return "redirect:/post/{id}";
  }

  // 삭제(단일)
  @GetMapping("/{id}/del")
  public String delete(
      @PathVariable("id") Long postId) {
    int rowss = postSVC.deleteById(postId);
    return "redirect:/post/list";
  }
  // 삭제(여러건)
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("postIds") List<Long> postIds){
    log.info("postIds={}", postIds);

    int rows = postSVC.deleteByIds(postIds);
    log.info("글목록 {}-건 삭제됨!",rows);
    return "redirect:/post/list";
  }

}
