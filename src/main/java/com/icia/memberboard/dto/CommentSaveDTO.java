package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveDTO {
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime commentDate;
}
