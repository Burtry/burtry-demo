package icu.burtry.writespacemodel.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleSearchVO {

    private Long id;

    private Long authorId;

    private String authorName;

    private String channelName;

    private String images;

    private Integer likes;

    private Integer comments;

    private Integer views;

    private LocalDateTime publishTime;

    private String title;

    private String content;



}
