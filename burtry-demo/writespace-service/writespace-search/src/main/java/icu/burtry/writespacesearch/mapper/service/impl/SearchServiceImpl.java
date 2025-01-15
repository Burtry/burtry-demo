package icu.burtry.writespacesearch.mapper.service.impl;

import cn.hutool.core.bean.BeanUtil;
import icu.burtry.writespacemodel.dto.SearchDTO;
import icu.burtry.writespacemodel.vo.ArticleSearchVO;
import icu.burtry.writespacesearch.mapper.service.SearchService;
import icu.burtry.writespacesearch.result.SearchResult;
import icu.burtry.writespaceutils.result.Result;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public SearchResult<List<ArticleSearchVO>> search(SearchDTO searchDTO) {
        if (BeanUtil.isEmpty(searchDTO)) {
            return SearchResult.error("参数异常，请重试");
        }

        SearchRequest searchRequest = new SearchRequest("article_info");
        searchRequest.source().query(QueryBuilders.multiMatchQuery(searchDTO.getKeyWords(),"title","content"));
        searchRequest.source().highlighter(new HighlightBuilder().field("title").requireFieldMatch(false).field("content").requireFieldMatch(false));

        // 分页参数设置
        int pageNum = searchDTO.getPageNum() != null ? searchDTO.getPageNum() : 1; // 默认第一页
        int pageSize = searchDTO.getPageSize() != null ? searchDTO.getPageSize() : 10; // 默认每页10条
        int from = (pageNum - 1) * pageSize; // 计算起始位置

        searchRequest.source()
                .from(from) // 起始位置
                .size(pageSize); // 每页数量

        SearchResponse response;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<ArticleSearchVO> list = handleResponse(response);
        long total = response.getHits().getTotalHits().value;

        return SearchResult.success(list,"查询成功",total,pageNum,pageSize);

    }

    private List<ArticleSearchVO> handleResponse(SearchResponse response) {
        List<ArticleSearchVO> resultList = new ArrayList<>();

        // 遍历搜索结果
        for (SearchHit hit : response.getHits().getHits()) {
            // 获取原始文档数据
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            // 创建 ArticleSearchVO 对象
            ArticleSearchVO articleSearchVO = new ArticleSearchVO();

            // 填充字段
            articleSearchVO.setId(Long.parseLong(sourceAsMap.get("id").toString()));
            articleSearchVO.setAuthorId(Long.parseLong(sourceAsMap.get("authorId").toString()));
            articleSearchVO.setAuthorName((String) sourceAsMap.get("authorName"));
            articleSearchVO.setChannelName((String) sourceAsMap.get("channelName"));
            articleSearchVO.setImages((String) sourceAsMap.get("images"));
            articleSearchVO.setLikes(Integer.parseInt(sourceAsMap.get("likes").toString()));
            articleSearchVO.setComments(Integer.parseInt(sourceAsMap.get("comments").toString()));
            articleSearchVO.setViews(Integer.parseInt(sourceAsMap.get("views").toString()));
            articleSearchVO.setPublishTime(LocalDateTime.parse(sourceAsMap.get("publishTime").toString()));

            // 提取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("title")) {
                Text[] titleFragments = highlightFields.get("title").fragments();
                StringBuilder highlightedTitle = new StringBuilder();
                for (Text fragment : titleFragments) {
                    highlightedTitle.append(fragment.string());
                }
                articleSearchVO.setTitle(highlightedTitle.toString()); // 设置高亮后的标题
            } else {
                articleSearchVO.setTitle((String) sourceAsMap.get("title")); // 未高亮时，设置原始标题
            }

            if (highlightFields.containsKey("content")) {
                Text[] contentFragments = highlightFields.get("content").fragments();
                StringBuilder highlightedContent = new StringBuilder();
                for (Text fragment : contentFragments) {
                    highlightedContent.append(fragment.string());
                }
                articleSearchVO.setContent(highlightedContent.toString()); // 设置高亮后的内容
            } else {
                articleSearchVO.setContent((String) sourceAsMap.get("content")); // 未高亮时，设置原始内容
            }

            // 将 ArticleSearchVO 对象添加到结果列表
            resultList.add(articleSearchVO);
        }

        return resultList; // 返回结果列表
    }
}
