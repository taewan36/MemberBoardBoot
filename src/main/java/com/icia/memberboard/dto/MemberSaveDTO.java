package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveDTO {
    @NotBlank(message = "필수입력 항목입니다.")
    @Length(min = 2, max = 20, message = "5~20자로 입력하세요")
    private String memberEmail;
    @NotBlank(message = "필수입력 항목입니다.")
    @Length(min = 2, max = 20, message = "5~20자로 입력하세요")
    private String memberPassword;
    @NotBlank(message = "필수입력 항목입니다.")
    @Length(min = 2, max = 20, message = "5~20자로 입력하세요")
    private String memberName;
    @NotBlank(message = "필수입력 항목입니다.")
    @Length(min = 2, max = 20, message = "5~20자로 입력하세요")
    private String memberPhone;

    private MultipartFile memberFile;

    private String memberFilename;

    private LocalDateTime memberDate;

    public MemberSaveDTO(String memberEmail, String memberPassword, String memberName, String memberPhone, String memberFilename) {
    }
}