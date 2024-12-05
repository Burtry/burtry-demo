package icu.burtry.writespacearticle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespaceutils.result.Result;

public interface IArticleService extends IService<Article> {

    /**
     * 发布文章
     * @param articleDTO
     * @return
     */
    Result saveArticle(ArticleDTO articleDTO);
}
