package icu.burtry.writespaceadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.burtry.writespacemodel.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
