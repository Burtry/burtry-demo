package icu.burtry.writespacearticle.fegin;

import icu.burtry.apis.article.IArticleClient;
import icu.burtry.writespacearticle.service.IArticleService;

import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import icu.burtry.writespacemodel.entity.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleClient implements IArticleClient {

    @Autowired
    private IArticleService articleService;

    @GetMapping("/getArticleData/{articleId}")
    @Override
    public ArticleDataDTO getArticleDataById(@PathVariable("articleId") Long articleId) {
        Article article = articleService.getById(articleId);

        ArticleDataDTO articleDataDTO = new ArticleDataDTO();
        articleDataDTO.setArticleId(articleId);
        articleDataDTO.setLikes(article.getLikes());
        articleDataDTO.setViews(article.getViews());
        articleDataDTO.setCollects(article.getCollections());
        return articleDataDTO;
    }
}
