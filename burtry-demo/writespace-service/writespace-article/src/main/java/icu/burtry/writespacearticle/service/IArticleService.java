package icu.burtry.writespacearticle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleContentVO;
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
    Result<List<ArticleVO>> getArticleList(Long id,Integer status);

    /**
     * 用户删除本人文章
     * @param id
     * @return
     */
    Result deleteArticle(Long id);

    /**
     * 获取文章数据总览
     * @param id
     */
    Result overView(Long id);

    /**
     * 根据文章id查询文章VO
     * @param id
     * @return
     */
    Result<ArticleContentVO> getArticleVOById(Long id);
}
