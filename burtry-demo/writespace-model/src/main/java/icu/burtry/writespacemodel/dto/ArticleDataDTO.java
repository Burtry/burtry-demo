package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class ArticleDataDTO {

    private Long articleId;

    private Integer likes;

    private Integer views;

    private Integer collects;

    // 设置单个属性的便捷方法
    public void incrementViews(Integer views) {
        this.views = (this.views == null ? 0 : this.views) + (views == null ? 0 : views);
    }


}
