package icu.burtry.writespacearticle.fegin;

import icu.burtry.apis.article.IArticleClient;
import icu.burtry.writespacearticle.service.IArticleService;

import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import icu.burtry.writespacemodel.entity.article.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/article")
@Slf4j
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

    @PostMapping("/postBehaviorData")
    public void postBehaviorData(@RequestBody Map<Long,ArticleDataDTO> map) {
        log.info("用户行为存储到数据库:{}",map);
        articleService.postData(map);
    }
}
