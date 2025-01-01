package icu.burtry.writespacemodel.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {

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

    /**
     * 文章状态     // 1草稿 ,2待审核
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Long publishTime;

    //快速判空
    public boolean hasNullOrEmptyFields() {
        return title == null || title.isBlank()
                || content == null || content.isBlank()
                || channelId == null
                || closeComment == null
                || images == null || images.isBlank();
    }
}
