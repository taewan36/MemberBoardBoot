package com.icia.memberboard.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateDTO {
    private Long boardId;
    private String boardWriter;
    private String boardPassword;
    private String boardTitle;
    private String boardContents;
    private MultipartFile boardFile;
    private String boardFilename;
    //    private LocalDateTime boardDate;
    private int boardHits;
}
