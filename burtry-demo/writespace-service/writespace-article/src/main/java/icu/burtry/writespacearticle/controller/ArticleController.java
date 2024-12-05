package icu.burtry.writespacearticle.controller;

import icu.burtry.writespacearticle.service.IArticleService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/article")
@Slf4j
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @PostMapping()
    public Result saveArticle(@RequestBody ArticleDTO articleDTO) {
        log.info("保存文章:{}", articleDTO);
        return articleService.saveArticle(articleDTO);
    }

}
