package icu.burtry.writespaceschedule.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import icu.burtry.apis.article.IArticleClient;
import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import icu.burtry.writespacemodel.entity.UserLike;
import icu.burtry.writespaceschedule.mapper.UserCollectMapper;
import icu.burtry.writespaceschedule.mapper.UserLikeMapper;
import icu.burtry.writespaceschedule.service.AsyncTaskService;
import icu.burtry.writespaceutils.constant.BehaviorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ScheduleJob {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Autowired
    private IArticleClient articleClient;

    @Autowired
    private UserLikeMapper userLikeMapper;

    @Autowired
    private UserCollectMapper userCollectMapper;

    @XxlJob("writespaceScheduleHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("执行用户行为数据刷新到数据库定时任务...");
        Map<Object, Object> readAllData = redisTemplate.opsForHash().entries(BehaviorConstants.READ_BEHAVIOR);
        Map<Object, Object> likeAllData = redisTemplate.opsForHash().entries(BehaviorConstants.LIKE_BEHAVIOR);
        Map<Object, Object> collectAllData = redisTemplate.opsForHash().entries(BehaviorConstants.COLLECTION_BEHAVIOR);

        // 创建一个新 Map，用于存储转换后的数据
        Map<Long, ArticleDataDTO> convertedData = new HashMap<>();

        //阅读
        for (Map.Entry<Object, Object> entry : readAllData.entrySet()) {
            // 将 key 转换为 Long 类型    key 为 文章id    articleId:阅读数
            Long readKey = Long.valueOf(entry.getKey().toString());
            // 将 value 转换为 Integer 类型
            Integer readValue = Integer.valueOf(entry.getValue().toString());

            // 获取或初始化 ArticleDataDTO
            convertedData.computeIfAbsent(readKey, id -> {
                ArticleDataDTO dto = new ArticleDataDTO();
                dto.setArticleId(readKey); // 设置 articleId
                return dto;
            }).incrementViews(readValue);
        }

        //点赞
        for (Map.Entry<Object, Object> entry : likeAllData.entrySet()) {
            // 将 key 转换为 Long 类型
            Long likeKey = Long.valueOf(entry.getKey().toString());
            // 将 value 转换为 Integer 类型
            Integer likeValue = Integer.valueOf(entry.getValue().toString());

            convertedData.computeIfAbsent(likeKey, id -> {
                ArticleDataDTO dto = new ArticleDataDTO();
                dto.setArticleId(likeKey); // 设置 articleId
                return dto;
            }).setLikes(likeValue);

        }

        for (Map.Entry<Object, Object> entry : collectAllData.entrySet()) {
            // 将 key 转换为 Long 类型
            Long collectKey = Long.valueOf(entry.getKey().toString());
            // 将 value 转换为 Integer 类型
            Integer collectValue = Integer.valueOf(entry.getValue().toString());

            convertedData.computeIfAbsent(collectKey, id -> {
                ArticleDataDTO dto = new ArticleDataDTO();
                dto.setArticleId(collectKey); // 设置 articleId
                return dto;
            }).setCollects(collectValue);

        }
        //将数据批量写入数据库
        asyncTaskService.postBehaviorDataAsync(convertedData, articleClient);

        //记录用户点赞、收藏
        Set<String> likeKeys = redisTemplate.keys(BehaviorConstants.LIKE_BEHAVIOR + "_*");
        Set<String> collectKeys = redisTemplate.keys(BehaviorConstants.COLLECTION_BEHAVIOR + "_*");

        //LIKE_BEHAVIOR_userId_articleId
        //COLLCET_BEHAVIOR_userId_articleId
        List<UserLike> likeList = new ArrayList<>();
        List<UserLike> collectList = new ArrayList<>();

        ToList(likeKeys, likeList);
        ToList(collectKeys, collectList);
        //批量添加

        if(!likeList.isEmpty()) {
            userLikeMapper.batchInsertLikes(likeList);
        }
        if (!collectList.isEmpty()) {
            userCollectMapper.batchInsertCollects(collectList);
        }
    }


    private void ToList(Set<String> sets, List<UserLike> list) {
        if (sets != null) {
            for (String key : sets) {
                String[] split = key.split("_");
                if (split.length == 3) {
                    Long userId = Long.parseLong(split[1]);
                    Long articleId = Long.parseLong(split[2]);

                        //UserLike 与 UserCollection 属性相同
                        UserLike articleLike = new UserLike();
                        articleLike.setUserId(userId);
                        articleLike.setArticleId(articleId);
                        articleLike.setCreateTime(LocalDateTime.now());
                        // 添加到列表中
                    list.add(articleLike);

                }
            }
        }
    }
}
