package icu.burtry.writespacesearch.mapper.controller;

import icu.burtry.writespacemodel.dto.SearchDTO;
import icu.burtry.writespacemodel.vo.ArticleSearchVO;
import icu.burtry.writespacesearch.mapper.service.SearchService;
import icu.burtry.writespacesearch.result.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@Slf4j
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("")
    public SearchResult<List<ArticleSearchVO>> search(@RequestBody SearchDTO searchDTO) {
      log.info("文章搜索:{}",searchDTO);
      return searchService.search(searchDTO);
    }
}
