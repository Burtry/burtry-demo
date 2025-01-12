package icu.burtry.writespacemodel.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminArticleSearchDTO {

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 页面数
     */
    private Integer pageSize;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 文章状态
     */
    private Integer status;

    /**
     * 频道id
     */
    private Long channelId;

    /**
     * 日期范围 开始时间
     */
    private String startDateTime;

    /**
     * 结束时间
     */
    private String endDateTime;
}
