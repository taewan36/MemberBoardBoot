package com.icia.memberboard.controller;

import com.icia.memberboard.common.PagingConst;
import com.icia.memberboard.dto.BoardDetailDTO;
import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardUpdateDTO;
import com.icia.memberboard.dto.CommentDetailDTO;
import com.icia.memberboard.service.BoardService;
import com.icia.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService bs;
    private final CommentService cs;

    // 글쓰기 양식
    @GetMapping("/save")
    public String saveForm() {
        return "board/save";
    }

    // 글쓰기 처리
    @PostMapping("/save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IOException {
        Long boardId = bs.save(boardSaveDTO);
        return "redirect:/board";
    }

    // 글목록
    @GetMapping("/")
    public String findAll(Model model) {
        List<BoardDetailDTO> boardList = bs.findAll();
        model.addAttribute("boardList", boardList);
        return "board/findAll";
    }

    //글 상세조회
    @GetMapping("/{boardId}")
    public String findById(@PathVariable Long boardId, Model model) {
        BoardDetailDTO board = bs.findById(boardId);
        bs.hits(boardId);
        model.addAttribute("board", board);
        List<CommentDetailDTO> commentList = cs.findAll(boardId);
        model.addAttribute("commentList", commentList);
        return "board/findById";
    }

    // 글 수정 화면
    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board", board);
        return "board/update";
    }

    // 글수정 처리
    @PutMapping("{boardId}")
    public ResponseEntity boardUpdate(@ModelAttribute BoardUpdateDTO boardUpdateDTO)throws IllegalStateException, IOException{
        Long boardId = bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // Paging 처리(board?page=5)
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDetailDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/paging";
    }

    // 글 삭제(/member/delete/5)
    @GetMapping("/delete/{boardId}")
    public String deleteById(@PathVariable("boardId") Long boardId) {
        bs.deleteById(boardId);
        return "redirect:/board/";
    }

    //글 검색(search)
    @GetMapping("/search")
    public String search(@RequestParam("searchType") String searchType,
                         @RequestParam("keyword") String keyword, Model model) {
        List<BoardDetailDTO> boardList=bs.search(searchType, keyword);
        System.out.println("controller : " + boardList);
        model.addAttribute("boardList", boardList);
        return "board/search";
    }



}