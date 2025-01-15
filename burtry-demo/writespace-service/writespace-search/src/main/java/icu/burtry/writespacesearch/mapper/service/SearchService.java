package icu.burtry.writespacesearch.mapper.service;

import icu.burtry.writespacemodel.dto.SearchDTO;
import icu.burtry.writespacemodel.vo.ArticleSearchVO;
import icu.burtry.writespacesearch.result.SearchResult;

import java.util.List;

public interface SearchService {
    /**
     * 文章搜索
     * @param searchDTO
     * @return
     */
    SearchResult<List<ArticleSearchVO>> search(SearchDTO searchDTO);
}
