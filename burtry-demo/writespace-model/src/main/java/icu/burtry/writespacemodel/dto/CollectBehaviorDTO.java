package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class CollectBehaviorDTO {
    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 1收藏
     * 0取消收藏
     */
    private Integer operation;
}
