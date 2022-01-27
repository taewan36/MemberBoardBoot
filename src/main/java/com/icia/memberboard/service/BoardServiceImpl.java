package com.icia.memberboard.service;

import com.icia.memberboard.common.PagingConst;
import com.icia.memberboard.dto.BoardDetailDTO;
import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardUpdateDTO;
import com.icia.memberboard.entity.BoardEntity;
import com.icia.memberboard.entity.MemberEntity;
import com.icia.memberboard.repository.BoardRepository;
import com.icia.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) throws IOException {
        MultipartFile file = boardSaveDTO.getBoardFile();
        String filename = file.getOriginalFilename();
        filename = System.currentTimeMillis() + filename;
        String savepath = "C:\\development\\source\\spring\\springboot\\MemberBoardProject\\src\\main\\resources\\static\\upload\\" + filename;
        if (!file.isEmpty()) {
            file.transferTo(new File(savepath));
        }
        boardSaveDTO.setBoardFilename(filename);
        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getBoardWriter());
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardSaveDTO, memberEntity);
        Long boardId = br.save(boardEntity).getId();
        return boardId;
    }

    @Override
    public List<BoardDetailDTO> findAll() {
        List<BoardEntity> boardEntityList = br.findAll();
        List<BoardDetailDTO> boardList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardList.add(BoardDetailDTO.toBoardDetailDTO(boardEntity));
        }
        return boardList;
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        Optional<BoardEntity> optionalBoardEntity = br.findById(boardId);
        BoardDetailDTO boardDetailDTO = null;
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        }
        return boardDetailDTO;
    }

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardUpdateDTO);
        return br.save(boardEntity).getId();

    }

    @Override
    public Page<BoardDetailDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1인면 페이지값을 0으로 하고 1이 아니면 요청페이지에서 1을 뺀다.
//        page = page -1;
        page = (page == 1) ? 0 : (page - 1);
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page<BoardEntity> => Page<BoardPagingDTO>
        Page<BoardDetailDTO> boardList = boardEntities.map(
                board -> new BoardDetailDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardFilename(),
                        board.getCreateTime(),
                        board.getBoardHits())
//                        board.getBoardHits()
        );
        return boardList;
    }

    @Override
    public void deleteById(Long boardId) {
        br.deleteById(boardId);
    }

    @Override
    public List<BoardDetailDTO> search(String searchType, String keyword) {

        List<BoardEntity> boardEntity = null;

        if(searchType.equals("boardTitle")){
            System.out.println("title");
            boardEntity = br.findByBoardTitleContaining(keyword);
        }else if(searchType.equals("boardWriter")){
            System.out.println("writer");
            boardEntity=br.findByBoardWriterContaining(keyword);
        }else{
            System.out.println("contents");
            boardEntity=br.findByBoardContentsContaining(keyword);
            System.out.println("asdfsadf="+boardEntity);
        }

        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for(BoardEntity b: boardEntity){
            boardDetailDTOList.add(BoardDetailDTO.toBoardDetailDTO(b));
        }
        return boardDetailDTOList;
    }

    @Transactional
    @Override
    public void hits(Long boardId) {
        br.hits(boardId);
    }

//    @Override
//    public List<BoardDetailDTO> search(String searchtype, String keyword) {
//        searchParam.put("type",  searchtype);
//        searchParam.put("word", keyword);
//        List<BoardDetailDTO> boardList = br.search(searchParam);
//        return boardList;
//    }
//

}


