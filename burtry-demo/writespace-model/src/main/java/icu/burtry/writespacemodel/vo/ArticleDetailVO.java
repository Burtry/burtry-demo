package icu.burtry.writespacemodel.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDetailVO {
    /**
     * 文章id
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 编辑时间
     */
    private LocalDateTime updateTime;

    /**
     * 作者头像
     */
    private String userAvatar;

    /**
     * 作者昵称
     */
    private String username;

    /**
     * 作者id
     */
    private Long userId;



}
