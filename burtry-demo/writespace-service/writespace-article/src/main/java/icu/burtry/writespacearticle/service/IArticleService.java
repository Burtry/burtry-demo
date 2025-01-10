package icu.burtry.writespacearticle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import icu.burtry.writespacemodel.dto.ArticleLoadDTO;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleContentVO;
import icu.burtry.writespacemodel.vo.ArticleDetailVO;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespaceutils.result.Result;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据文章id获取文章详情VO
     * @param id
     * @return
     */
    Result<ArticleDetailVO> getArticleDetailById(Long id);

    /**
     * 加载首页文章列表
     * @param
     * @return
     */
    Result<List<ArticleVO>> load(ArticleLoadDTO articleLoadDTO);

    /**
     * 用户行为数据存储到数据库
     * @param
     */
    void postData(Map<Long,ArticleDataDTO> map);


}
