package com.study5.seoul.bike.controller;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Comment;
import com.study5.seoul.bike.service.BoardService;
import com.study5.seoul.bike.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BoardService boardService;

// 리퀘스트매핑.


    @PostMapping("/board/{id}/comment")
    public String createComment(@PathVariable("id") Long id, @RequestParam("content") String content) {
        commentService.create(id, content);
        return "redirect:/board/{id}";
    }

    @GetMapping("/board/{boardId}/comment/{commentId}/edit")
    public String editComment(@PathVariable Long boardId, @PathVariable Long commentId, Model model) {
        Comment comment = commentService.findOne(commentId); // 댓글 ID를 통해 해당 댓글을 가져옵니다.
        model.addAttribute("comment", comment); // 모델에 댓글 정보를 추가합니다.

        return "edit-comment"; // 수정할 댓글을 편집하는 페이지로 이동
    }

    @PostMapping("/board/{boardId}/comment/{commentId}/edit")
    public String updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestParam("content") String content) {
        commentService.regComment(commentId,content);

        return "redirect:/board/{boardId}";
    }

    @GetMapping("/board/{boardId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.delComment(commentId);
        return "redirect:/board/{boardId}"; // 댓글 삭제 후 댓글을 편집하는 페이지로 리다이렉트
    }

}




