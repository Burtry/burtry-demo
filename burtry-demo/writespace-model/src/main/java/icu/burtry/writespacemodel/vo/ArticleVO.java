package icu.burtry.writespacemodel.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleVO {

    private String title;

    private Long id;

    private Integer likes;

    private Integer views;

    private Integer comments;

    private String content;

    private String userAvatar;

    private String username;

    private String image;

    private LocalDateTime publishTime;
}
