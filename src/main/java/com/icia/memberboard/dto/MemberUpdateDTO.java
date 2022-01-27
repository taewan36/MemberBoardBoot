package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDTO {
    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String  memberName;
    private String memberPhone;
    private MultipartFile memberFile;
    private String memberFilename;

    public MemberUpdateDTO(Long memberId, String memberEmail, String memberPassword, String memberName, String memberPhone, String memberFilename) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.memberFilename = memberFilename;
    }
}
