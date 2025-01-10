package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class CommentPageQueryDTO {
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

}
