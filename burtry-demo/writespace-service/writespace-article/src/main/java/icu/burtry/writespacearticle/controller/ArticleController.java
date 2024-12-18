package icu.burtry.writespacearticle.controller;

import icu.burtry.writespacearticle.service.IArticleService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @DeleteMapping("{id}")
    public Result deleteArticleById(@PathVariable("id") Long id) {
        log.info("用户删除文章:{}",id);
        return articleService.deleteArticle(id);

    }

    @GetMapping("{id}")
    public Result<List<ArticleVO>> getArticleList(@PathVariable("id") Long id) {
        log.info("获取请求用户的文章列表:{}",id);
        return articleService.getArticleList(id);

    }


}
