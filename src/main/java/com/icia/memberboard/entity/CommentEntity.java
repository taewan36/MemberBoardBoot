package com.icia.memberboard.entity;

import com.icia.memberboard.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    // 원글의 게시글 번호를 참조하기 위한 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 부모테이블(참조하고자 하는 테이블 )의 PK 컬럼이름을 쓴다.
    private BoardEntity boardEntity; // 참조하고자 하는 테이블을 관리하는 Entity

    // 댓글과 멤버의 연관관계(N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 부모테이블(참조하고자 하는 테이블 )의 PK 컬럼이름을 쓴다.
    private MemberEntity memberEntity; // 참조하고자 하는 테이블을 관리하는 Entity

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    public static CommentEntity toSaveEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentSaveDTO.getCommentWriter());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setMemberEntity(memberEntity);
        return commentEntity;
    }
}
