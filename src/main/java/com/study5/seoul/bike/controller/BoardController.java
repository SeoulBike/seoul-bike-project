package com.study5.seoul.bike.controller;

import com.study5.seoul.bike.domain.Board;
import com.study5.seoul.bike.domain.Comment;
import com.study5.seoul.bike.domain.Like;
import com.study5.seoul.bike.service.BoardService;
import com.study5.seoul.bike.service.LikeService;
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
    private final LikeService likeService;

    //특정 게시물 조회
    @GetMapping("/board/{id}")
    public String showBoardId(@PathVariable("id") Long id, Model model) {

        Board board = boardService.findBoardWithComments(id);
        //좋아요 부분
        List <Board> likes = boardService.getLikeCount(id);
        int likeCount = likes.size();
        model.addAttribute("board", board);
        model.addAttribute("likeCount",likeCount);
        System.out.println(likeCount);
        return "board-detail";
    }


//    //리스트 조회해서 뿌려줌
//    @GetMapping("/")
//    public String showList(Model model){
//        List <Board> boardList = boardService.findAllNotDeletedWithComments();
//        model.addAttribute("boardList",boardList);
//        return "board";
//    }

    //리스트 조회해서 뿌려줌 ver 2 ( paging )
    @GetMapping("/board")
    public String showList(@RequestParam(defaultValue = "1")int page, Model model){
        int pageSize = 10;
        int totalCount = boardService.getTotalCount();;
        int totalPages = (int)Math.ceil((double) totalCount/pageSize);

        List<Board> boardList = boardService.findAllNotDeletedWithCommentsVer2(page,pageSize);

        model.addAttribute("boardList",boardList);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalPages);

        return "board";
    }

    //작성 버튼
    @GetMapping("/board/create-form")
    public String showCreateForm() {
        return "create-form";
    }


    //작성
    @PostMapping("/board/create")
    public String createBoard(@RequestParam("title") String title, @RequestParam("content") String content) {
       boardService.create(title, content);
       return "redirect:/board";
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
        return "redirect:";
    }

    //삭제
    @GetMapping("/board/{id}/delete")
    public String deleteBoard(@PathVariable("id") Long id) {
        boardService.delBoard(id);
        return "redirect:/board";
    }

    //좋아요
    @PostMapping("/board/{id}/like")
    public String likeBoard(@PathVariable("id") Long id) {
        likeService.doLike(id);
        return "redirect:/board/{id}";
    }

    //좋아요 취소 // 아직 프론트 단에 추가하지 않았음. 언제든 가능(쉬움)
    @PostMapping("/board/{id}/unlike")
    public String unlikeBoard(@PathVariable("id") Long id) {
        likeService.doNotLike(id);
        return "redirect:/board/{id}";
    }

    //검색 기능 + 검색된 리스트들을 페이징 해서 보여줄 수 있도록
    @GetMapping("/board/search")
    public String search(Model model,@RequestParam("title")String title, @RequestParam(defaultValue = "1")int page){
        int pageSize = 10;
        int totalCount = boardService.getTotalCount();;
        int totalPages = (int)Math.ceil((double) totalCount/pageSize);
        List<Board> boardList = boardService.findByTitle(title,page,pageSize);
        model.addAttribute(boardList);

        model.addAttribute("boardList",boardList);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalPages);


        return "search-list";
    }

}

