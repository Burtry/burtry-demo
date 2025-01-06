package icu.burtry.apis.article;

import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("writespace-article")
public interface IArticleClient {

    @GetMapping("/api/v1/article/getArticleData/{articleId}")
    ArticleDataDTO getArticleDataById(@PathVariable("articleId") Long articleId);
}
