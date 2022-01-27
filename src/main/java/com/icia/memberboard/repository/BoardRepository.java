package com.icia.memberboard.repository;

import com.icia.memberboard.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>  {

    @Modifying
    @Query(value = "update BoardEntity as b set b.boardHits = b.boardHits+1 where b.id = :boardId")
    void hits(@Param("boardId") Long boardId);


    List<BoardEntity> findByBoardTitleContaining(String keyword);

    List<BoardEntity> findByBoardWriterContaining(String keyword);

    List<BoardEntity> findByBoardContentsContaining(String keyword);
}
