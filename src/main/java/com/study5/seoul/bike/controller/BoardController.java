package com.study5.seoul.bike.controller;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //특정 게시물 조회
    @GetMapping("/board/{id}")
    public String showBoardId(@PathVariable("id") Long id, Model model){
        Board board = boardService.findOne(id);
        model.addAttribute("board",board);
        return "board";
    }

    //리스트 조회해서 뿌려줌
    @GetMapping("/")
    public String showList(Model model){
        List <Board> boardList = boardService.findAllNotDeletedWithComments();
        model.addAttribute("boardList",boardList);
        return "board";
    }

    //작성
    @PostMapping("/board")
    public String createPost(@RequestParam("title") String title, @RequestParam("content") String content) {
       boardService.create(title, content);
       return "redirect:/";
    }

    //수정 버튼
    @GetMapping("/board/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Board board = boardService.findOne(id);
        model.addAttribute("board", board);
        return "editForm";
    }

    //수정
    @PostMapping("/board/{id}/update")
    public String regBoard(@PathVariable("id") Long id,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content) {
        boardService.regBoard(id, title, content);
        return "redirect:/";
    }

    //삭제
    @GetMapping("/board/{id}/delete")
    public String deleteBoard(@PathVariable("id") Long id) {
        boardService.delBoard(id);
        return "redirect:/";
    }


}

