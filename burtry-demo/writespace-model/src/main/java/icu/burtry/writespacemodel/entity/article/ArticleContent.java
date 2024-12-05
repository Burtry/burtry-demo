package icu.burtry.writespacemodel.entity.article;

import lombok.Data;

@Data
public class ArticleContent {

    /**
     * 文章内容id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 文章内容
     */
    private String content;
}
