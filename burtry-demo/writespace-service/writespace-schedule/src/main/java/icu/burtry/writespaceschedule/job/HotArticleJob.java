package icu.burtry.writespaceschedule.job;

import com.alibaba.fastjson.JSON;
import com.xxl.job.core.handler.annotation.XxlJob;
import icu.burtry.apis.user.IUserClient;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespacemodel.vo.HotArticleVO;
import icu.burtry.writespaceschedule.mapper.ArticleMapper;
import icu.burtry.writespaceutils.constant.BehaviorConstants;
import icu.burtry.writespaceutils.constant.ScoreConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class HotArticleJob {

    private static final double LOG_CONSTANT = 10.0; // c 的值，可以根据业务调整

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private IUserClient userClient;

    @XxlJob("hotArticleJobHandler")
    @Async
    public void hotArticleJob() {

        //先将redis中的热点文章删除
        redisTemplate.delete("hotArticleList");

        //获取redis 中的数据
        Map<Object, Object> map = redisTemplate.opsForHash().entries(BehaviorConstants.READ_BEHAVIOR);

        List<Long> articleIdList = new ArrayList<>();

        map.forEach((key, value) -> {
            articleIdList.add(Long.valueOf(key.toString()));
        });


        //获得本日操作过(点赞、阅读、收藏)的文章
        List<Article> ToDayarticleList = articleMapper.selectListByIds(articleIdList);

        //获得前三天的发布的文章数据，不包括本日操作过的文章
        List<Article> threeDaysArticleList = articleMapper.selectArticlesWithinThreeDays(articleIdList);

        //计算文章分值
        //进行热点文章计算，将结果放入redis中,计算分值，阅读1分 点赞2分 收藏3分
        //本日操作过的文章推荐权重更高，在计算完当前分值之后, 进行log函数加权，即加权分数 = 原分数 * log(原分数 + c)
        //c用于控制权重增长速度  这里给个10
        List<HotArticleVO> hotArticleVOList = new ArrayList<>();

        for (Article article : ToDayarticleList) {

            HotArticleVO hotArticleVO = new HotArticleVO();
            BeanUtils.copyProperties(article, hotArticleVO);

            User user = userClient.findUserById(article.getAuthorId());
            hotArticleVO.setUserId(article.getAuthorId());
            hotArticleVO.setUsername(article.getAuthorName());
            hotArticleVO.setUserAvatar(user.getImage());
            hotArticleVO.setImage(article.getImages());
            String content = articleMapper.getContentById(article.getId());
            hotArticleVO.setContent(content);

            //本日操作过的文章进行权重加权,true
            int score = calculateWeightedScore(article,true);

            hotArticleVO.setScore(score);
            hotArticleVOList.add(hotArticleVO);
        }

        for (Article article : threeDaysArticleList) {
            HotArticleVO hotArticleVO = new HotArticleVO();
            BeanUtils.copyProperties(article, hotArticleVO);

            int score = calculateWeightedScore(article, false);
            hotArticleVO.setScore(score);
            hotArticleVOList.add(hotArticleVO);
        }

        //获取分数前20条添加到redis中
        List<HotArticleVO> top20HotArticle = hotArticleVOList.stream().sorted((a1, a2) -> Integer.compare(a2.getScore(), a1.getScore()))
                .limit(20)
                .collect(Collectors.toList());

        // 将前 20 条文章存入 Redis
        redisTemplate.opsForValue().set("hotArticleList",JSON.toJSONString(top20HotArticle));

    }

    public static int calculateWeightedScore(Article article,Boolean isToDay) {
        // 计算原始分数
        int score = 0;
        score += article.getLikes() * ScoreConstant.LIKE_SCORE;
        score += article.getViews() * ScoreConstant.READ_SCORE;
        score += article.getCollections() * ScoreConstant.COLLECT_SCORE;

        // 检查原始分数，防止 log(0) 的情况
        if (score <= 0) {
            return 0; // 如果原始分数为 0 或负数，则直接返回 0
        }

        // 使用 log 函数进行加权
        double weightedScore = 0;
        //是今日操作过的文章，执行加权
        if (isToDay) {
            weightedScore = score * Math.log(score + LOG_CONSTANT);
        }

        // 返回加权分数，取整
        return (int) Math.round(weightedScore);
    }
}
