package com.study5.seoul.bike.controller;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Comment;
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


    @PostMapping("/board/{id}/comment")
    public String createComment(@PathVariable("id") Long id, @RequestParam("content") String content) {
        commentService.create(id, content);
        return "redirect:/";
    }




}
