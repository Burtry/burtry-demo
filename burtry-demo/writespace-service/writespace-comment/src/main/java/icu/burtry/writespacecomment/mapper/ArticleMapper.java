package icu.burtry.writespacecomment.mapper;

import icu.burtry.writespacemodel.entity.article.ArticleConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleMapper {

    @Select("select * from writespace.article_config where article_id = #{articleId}")
    ArticleConfig getArticleById(Long articleId);
}
