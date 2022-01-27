package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDTO {
    @NotBlank(message = "필수입력 항목입니다.")
    @Length(min = 2, max = 20, message = "5~20자로 입력하세요")
    private String memberEmail;
    @NotBlank(message = "필수입력 항목입니다.")
    @Length(min = 2, max = 20, message = "5~20자로 입력하세요")
    private String memberPassword;

}

