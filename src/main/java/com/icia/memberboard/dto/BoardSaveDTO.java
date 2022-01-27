package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveDTO {
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private MultipartFile boardFile;
    private String boardFilename;

}
