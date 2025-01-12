package icu.burtry.writespaceadmin.controller;

import icu.burtry.writespaceadmin.service.IArticleService;
import icu.burtry.writespacemodel.dto.AdminArticleSearchDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.article.Article;
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

    @GetMapping("/channelList")
    public Result<List<Channel>> getChannelList() {
      log.info("获取频道列表");
      return articleService.getChannelList();
    }

    @PostMapping("/list")
    public Result<PageDTO<Article>> getArticleList(@RequestBody AdminArticleSearchDTO adminArticleSearchDTO) {
        log.info("管理端文章搜素:{}",adminArticleSearchDTO);
        return articleService.getArticleList(adminArticleSearchDTO);
    }
}
