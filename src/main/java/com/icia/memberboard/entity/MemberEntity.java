package com.icia.memberboard.entity;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import com.icia.memberboard.dto.MemberUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 20)
    private String memberEmail;

    @Column(length = 20)
    private String memberPassword;

    @Column(length = 30)
    private String memberName;

    @Column(length = 30)
    private String memberPhone;

    @Column(length = 30)
    private String memberFilename;




    // 멤버와 게시글 연관관계(1:N) : on delete set null을 할 때
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    // 멤버와 댓글 연관관계(1:N) : on delete set null을 할 때
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, orphanRemoval =false, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        System.out.println("MemberEntity.preRemove");
        boardEntityList.forEach(board -> board.setMemberEntity(null));
        commentEntityList.forEach(comment -> comment.setMemberEntity(null));
    }

    public static MemberEntity saveMember(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        memberEntity.setMemberPhone(memberSaveDTO.getMemberPhone());
        memberEntity.setMemberFilename(memberSaveDTO.getMemberFilename());

        return memberEntity;
    }

    public static MemberEntity toUpdateEntity(MemberUpdateDTO memberUpdateDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberUpdateDTO.getMemberId());
        memberEntity.setMemberEmail(memberUpdateDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberUpdateDTO.getMemberPassword());
        memberEntity.setMemberName(memberUpdateDTO.getMemberName());
        memberEntity.setMemberPhone(memberUpdateDTO.getMemberPhone());
//      boardEntity.setBoardDate(LocalDateTime.now());
        memberEntity.setMemberFilename(memberUpdateDTO.getMemberFilename());

        return memberEntity;
    }


}
