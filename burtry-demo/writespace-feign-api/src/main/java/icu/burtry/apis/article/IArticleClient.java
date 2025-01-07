package icu.burtry.apis.article;

import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("writespace-article")
public interface IArticleClient {

    @GetMapping("/api/v1/article/getArticleData/{articleId}")
    ArticleDataDTO getArticleDataById(@PathVariable("articleId") Long articleId);

    @PostMapping("/api/v1/article/postBehaviorData")
    void postBehaviorData(@RequestBody Map<Long,ArticleDataDTO> map);
}
