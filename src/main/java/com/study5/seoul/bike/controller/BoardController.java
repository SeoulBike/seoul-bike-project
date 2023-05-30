package com.study5.seoul.bike.controller;

import com.study5.seoul.bike.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public String show(){
        return "/board";
    }

    @PostMapping("/post")
    public String createPost(@RequestParam("title") String title, @RequestParam("content") String content) {
       boardService.create(title, content);
       return "redirect:/board";
    }

}
