package icu.burtry.writespacemodel.entity.article;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 频道id
     */
    private Long channelId;

    /**
     * 封面url
     */
    private String images;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 阅读数
     */
    private Integer views;

    /**
     * 收藏数
     */
    private Integer collections;

    /**
     * 评论数
     */
    private Integer comments;


    /**
     * 举报数
     */
    private Integer reports;

    /**
     * 文章状态
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime publishTime;

    private LocalDateTime updateTime;

}
