package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class ArticleDTO {

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
     * 是否关闭评论
     */
    private Boolean closeComment;

    /**
     * 封面url
     */
    private String images;
}
