package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class ArticleDataDTO {

    private Long articleId;

    private Integer likes;

    private Integer views;

    private Integer collects;

}
