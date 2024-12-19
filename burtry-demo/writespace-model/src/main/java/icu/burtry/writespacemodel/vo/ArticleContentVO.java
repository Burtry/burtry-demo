package icu.burtry.writespacemodel.vo;

import lombok.Data;

@Data
public class ArticleContentVO {

    /**
     * 文章id
     */
    public Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 频道id
     */
    private Long channelId;

    /**
     * 是否可评论
     */
    private Integer isComment;

    /**
     * 文章封面
     */
    private String image;
}
