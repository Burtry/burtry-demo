package icu.burtry.writespacearticle.listener;


import cn.hutool.dfa.WordTree;
import com.alibaba.fastjson.JSON;
import icu.burtry.writespacearticle.config.RabbitMQConfig;
import icu.burtry.writespacearticle.mapper.ArticleMapper;
import icu.burtry.writespacemodel.dto.ArticleVerifyMessageDTO;

import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleSearchVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Slf4j
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
public class HandlerArticleVerify {


    @Autowired
    private WordTree sensitiveFilter;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @RabbitHandler
    public void handlerArticleVerify(ArticleVerifyMessageDTO message) throws InterruptedException {
        log.info("进行敏感词过滤:{}",message);

        if (message == null || message.getArticleId() == null) {
            log.warn("Invalid message or articleId is null, skipping...");
            return;
        }

        List<String> contentSensitiveList = sensitiveFilter.matchAll(message.getContent());
        List<String> titleSensitiveList = sensitiveFilter.matchAll(message.getTitle());
        Article article = articleMapper.selectById(message.getArticleId());
        if(!contentSensitiveList.isEmpty() || !titleSensitiveList.isEmpty()) {
            //包含敏感词，将该文章锁定
            if(message.getArticleId() != null) {

                article.setStatus(5);   //设置文章已锁定状态
                articleMapper.updateById(article);
                log.info("文章id:{} 包含敏感词已锁定",message.getArticleId());
            }
        }else {
            //文章敏感词过滤通过，设置文章已发布状态
            //判断是否存在发布时间，存在设置为已审核待发布状态
            if (message.getIsScheduledPublish()) {
                article.setStatus(3);
                articleMapper.updateById(article);
            }else {
                //设置为已发布状态
                article.setStatus(4);
                articleMapper.updateById(article);

                ArticleSearchVO articleSearchVO = new ArticleSearchVO();
                BeanUtils.copyProperties(article,articleSearchVO);
                articleSearchVO.setContent(message.getContent());

                //向es中添加该文章

                IndexRequest indexRequest = new IndexRequest("article_info");
                indexRequest.id(article.getId().toString()).source(JSON.toJSONString(articleSearchVO), XContentType.JSON);

                try {
                    restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }
}
