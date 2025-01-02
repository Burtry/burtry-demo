package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private Long articleId;

    private Long userId;

    private String content;

}
