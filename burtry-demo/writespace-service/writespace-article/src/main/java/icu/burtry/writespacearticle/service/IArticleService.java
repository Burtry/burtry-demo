package icu.burtry.writespacearticle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespaceutils.result.Result;

import java.util.List;

public interface IArticleService extends IService<Article> {

    /**
     * 发布文章
     * @param articleDTO
     * @return
     */
    Result saveArticle(ArticleDTO articleDTO);


    /**
     * 获取用户的文章列表
     * @return
     */
    Result<List<ArticleVO>> getArticleList();

}
