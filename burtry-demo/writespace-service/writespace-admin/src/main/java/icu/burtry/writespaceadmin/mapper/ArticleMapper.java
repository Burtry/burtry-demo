package icu.burtry.writespaceadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.burtry.writespacemodel.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select content from writespace.article_content where article_id = #{id}")
    String getContent(Long id);

    @Update("update writespace.article_config set article_config.is_delete = 1 where article_id = #{id} ")
    void deleteArticle(Long id);
}
