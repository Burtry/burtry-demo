package icu.burtry.writespacemodel.entity.article;

import lombok.Data;

@Data
public class ArticleConfig {

    /**
     * 文章配置id
     */
    private Long id;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 是否可评论
     */
    private Integer isComment;
}
