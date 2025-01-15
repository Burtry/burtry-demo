package icu.burtry.writespacesearch.mapper;

import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleSearchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("select a.id, a.title, a.author_id, a.author_name, a.channel_name, a.images, a.likes, a.views, a.comments, a.publish_time,ac.content from writespace.article a left join writespace.article_content ac on a.id = ac.article_id where a.status = 4")
    List<ArticleSearchVO> getList();
}
