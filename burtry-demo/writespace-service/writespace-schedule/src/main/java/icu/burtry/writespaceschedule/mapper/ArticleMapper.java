package icu.burtry.writespaceschedule.mapper;

import icu.burtry.writespacemodel.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("select id, title, author_id, author_name, channel_id, " +
            "channel_name, images, likes, views, collections, " +
            "comments, reports, status, create_time, publish_time, update_time " +
            "from writespace.article where status = 3")
    List<Article> selectList();

    @Update("UPDATE writespace.article SET status = #{status} WHERE id = #{id}")
    void updateArticle(Article article);

    @Select("select writespace.article_content.content from writespace.article_content where article_id = #{id}")
    String getContentById(Long id);
}
