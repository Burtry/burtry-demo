package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class LikeBehaviorDTO {
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 1点赞
     * 0取消点赞
     */
    private Integer operation;
}
