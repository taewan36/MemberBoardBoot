package com.icia.memberboard.service;

import com.icia.memberboard.dto.BoardDetailDTO;
import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO) throws IOException;

    List<BoardDetailDTO> findAll();

    BoardDetailDTO findById(Long boardId);


    Long update(BoardUpdateDTO boardUpdateDTO);

    Page<BoardDetailDTO> paging(Pageable pageable);

    void deleteById(Long boardId);

    List<BoardDetailDTO> search(String searchtype, String keyword);

    void hits(Long boardId);
}
