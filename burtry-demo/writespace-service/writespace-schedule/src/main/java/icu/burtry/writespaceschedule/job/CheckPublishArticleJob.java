package icu.burtry.writespaceschedule.job;

import com.alibaba.fastjson.JSON;
import com.xxl.job.core.handler.annotation.XxlJob;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleSearchVO;
import icu.burtry.writespaceschedule.mapper.ArticleMapper;
import icu.burtry.writespaceutils.constant.ArticleStatusConstant;
import icu.burtry.writespaceutils.constant.BehaviorConstants;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用来检查已审核未发布文章是否到达发布时间，将这些文章修改状态为已发布
 */
@Component
@Slf4j
public class CheckPublishArticleJob {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @XxlJob("checkPublishArticleHandler")
    public void checkPublishArticleJob() {

      log.info("查询已审核为发布文章列表...");
        //查找文章状态为已审核待发布状态的文章
        List<Article> articleList = articleMapper.selectList();
        if(articleList.isEmpty()) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();

        List<ArticleSearchVO> articleSearchVOList = new ArrayList<>();
        for (Article article : articleList) {
            LocalDateTime publishTime = article.getPublishTime();
            //判断当前时间是否到达发布时间
            if (publishTime != null && !publishTime.isAfter(now)) {
                article.setStatus(ArticleStatusConstant.PUBLISHED);   //设置为已发布状态
                articleMapper.updateArticle(article);
            }
            //获得文章内容
            String content = articleMapper.getContentById(article.getId());
            ArticleSearchVO articleSearchVO = new ArticleSearchVO();
            BeanUtils.copyProperties(article,articleSearchVO);
            articleSearchVO.setContent(content);
            articleSearchVOList.add(articleSearchVO);
        }


        //向es中添加已发布文章
        BulkRequest bulkRequest = new BulkRequest();

        for (ArticleSearchVO articleSearchVO : articleSearchVOList) {
            IndexRequest indexRequest = new IndexRequest("article_info");
            indexRequest.id(articleSearchVO.getId().toString()).source(JSON.toJSONString(articleSearchVO), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        try {
            restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
