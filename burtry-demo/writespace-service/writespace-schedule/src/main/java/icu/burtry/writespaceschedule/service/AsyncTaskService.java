package icu.burtry.writespaceschedule.service;

import icu.burtry.apis.article.IArticleClient;
import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AsyncTaskService {

    @Async
    public void postBehaviorDataAsync(Map<Long, ArticleDataDTO> convertedData, IArticleClient articleClient) {
        articleClient.postBehaviorData(convertedData);
    }

}