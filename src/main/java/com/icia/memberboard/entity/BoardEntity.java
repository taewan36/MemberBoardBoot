package com.icia.memberboard.entity;

import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
// BaseEntity를 상속받는 자식 클래스로 설정
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id") //db에 생성될 이름
    private Long id;

    @Column(length = 100)
    private String boardTitle;

    @Column(length = 20)
    private String boardWriter;

    @Column(length = 2000)
    private String boardContents;

    @Column(length = 30)
    private String boardFilename;

    @Column
    private int boardHits;

    // 게시글과 댓글 연관관계
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    // 게시글과 회원 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 부모테이블(참조하고자 하는 테이블 )의 PK 컬럼이름을 쓴다.
    private MemberEntity memberEntity;


//    @Column(updatable = false)
//    private LocalDateTime boardDate;

    public static BoardEntity toSaveEntity(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
//        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
//        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setBoardFilename((boardSaveDTO.getBoardFilename()));
        boardEntity.setBoardHits(0);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardUpdateDTO boardUpdateDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardUpdateDTO.getBoardId());
        boardEntity.setBoardWriter(boardUpdateDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
        boardEntity.setBoardFilename(boardUpdateDTO.getBoardFilename());
        boardEntity.setBoardHits(boardUpdateDTO.getBoardHits());

        return boardEntity;
    }
}