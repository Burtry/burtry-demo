package icu.burtry.writespacearticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.burtry.writespacemodel.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 多表联查，查询未删除的文章列表
     * @param list
     * @return
     */
    List<Article> getNoDeleteArticle(List<Long> list,Long id,Integer status);
}
