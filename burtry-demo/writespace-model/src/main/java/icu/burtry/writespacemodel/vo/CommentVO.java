package icu.burtry.writespacemodel.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {

    private Long userId;

    private String userAvatar;

    private String username;

    private String content;

    private LocalDateTime publishTime;
}
