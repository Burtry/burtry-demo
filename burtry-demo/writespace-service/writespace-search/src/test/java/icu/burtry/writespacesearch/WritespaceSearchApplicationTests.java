package icu.burtry.writespacesearch;

import com.alibaba.fastjson.JSON;
import icu.burtry.writespacemodel.dto.SearchDTO;
import icu.burtry.writespacemodel.vo.ArticleSearchVO;
import icu.burtry.writespacesearch.mapper.ArticleMapper;
import icu.burtry.writespacesearch.service.SearchService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WritespaceSearchApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SearchService searchService;

    @Test
    void test() throws IOException {

        GetResponse response = restHighLevelClient.get(new GetRequest("article_info", "1877690541823627264"), RequestOptions.DEFAULT);

        restHighLevelClient.delete(new DeleteRequest("article_info", "1877690541823627264"), RequestOptions.DEFAULT);

        System.out.println(response);

    }

    @Test
    void bulkAddArticle() throws IOException {

        BulkRequest bulkRequest = new BulkRequest();

        //批量添加已审核通过的文章
        List<ArticleSearchVO> list = articleMapper.getList();

        for (ArticleSearchVO articleSearchVO : list) {
            IndexRequest indexRequest = new IndexRequest("article_info");
            indexRequest.id(articleSearchVO.getId().toString()).source(JSON.toJSONString(articleSearchVO), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

    }

    @Test
    void testSearch() throws IOException {

        SearchRequest searchRequest = new SearchRequest("article_info");

        searchRequest.source().query(QueryBuilders.multiMatchQuery("测试", "title", "content"));

        searchRequest.source().highlighter(new HighlightBuilder().field("title").requireFieldMatch(false).field("content").requireFieldMatch(false));

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        List<ArticleSearchVO> list = handleResponse(response);

        System.out.println(list);

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

    @Test
    void testAPI() throws IOException{
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setKeyWords("测试");
        searchDTO.setPageNum(1);
        searchDTO.setPageSize(10);
        //Result<List<ArticleSearchVO>> search = searchService.search(searchDTO);


        //System.out.println(search);

    }

}
