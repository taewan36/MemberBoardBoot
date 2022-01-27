package com.icia.memberboard.dto;

import com.icia.memberboard.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String  memberName;
    private String memberPhone;
    private MultipartFile memberFile;
    private String memberFilename;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity memberEntity){
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(memberEntity.getId());
        memberDetailDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDetailDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDetailDTO.setMemberName(memberEntity.getMemberName());
        memberDetailDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDetailDTO.setMemberFilename(memberEntity.getMemberFilename());
        memberDetailDTO.setCreateTime(memberEntity.getCreateTime());
        memberDetailDTO.setUpdateTime(memberEntity.getUpdateTime());
        return memberDetailDTO;

    }
}
