package icu.burtry.writespacesearch.mapper;

import icu.burtry.writespacemodel.entity.article.ArticleContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleContentMapper {

    @Select("select * from writespace.article_content where article_id = 1877690541823627264")
    ArticleContent getOne();
}
