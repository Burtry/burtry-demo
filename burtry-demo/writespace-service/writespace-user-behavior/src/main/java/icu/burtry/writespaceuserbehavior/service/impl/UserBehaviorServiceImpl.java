package icu.burtry.writespaceuserbehavior.service.impl;

import icu.burtry.apis.article.IArticleClient;
import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import icu.burtry.writespacemodel.dto.CollectBehaviorDTO;
import icu.burtry.writespacemodel.dto.LikeBehaviorDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.BehaviorDataVO;
import icu.burtry.writespaceuserbehavior.service.IUserBehaviorService;
import icu.burtry.writespaceutils.constant.BehaviorConstants;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.thread.UserThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserBehaviorServiceImpl implements IUserBehaviorService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private IArticleClient articleClient;


    @Override
    public Result like(LikeBehaviorDTO likeBehaviorDTO) {

        if (likeBehaviorDTO.getArticleId() == null || likeBehaviorDTO.getOperation() == null) {
            return Result.error("参数错误!");
        }

        User user = UserThreadLocalUtil.getUser();
        if(user == null || user.getId() == 0L) {
            return Result.error("需要登录");
        }

        long likes = 0L;    //LIKE-BEHAVIOR: {articleId : 点赞数}
        Object object = redisTemplate.opsForHash().get(BehaviorConstants.LIKE_BEHAVIOR, likeBehaviorDTO.getArticleId().toString());
        if(object !=null) {
            likes = Long.parseLong(object.toString());
        }

        if(likeBehaviorDTO.getOperation() == 1) {
            //点赞
            redisTemplate.opsForHash().put(BehaviorConstants.LIKE_BEHAVIOR,likeBehaviorDTO.getArticleId().toString(),String.valueOf(likes + 1));

            //标记自己已点赞
            redisTemplate.opsForValue().set(BehaviorConstants.LIKE_BEHAVIOR + "_" + likeBehaviorDTO.getArticleId(),user.getId().toString());

            return Result.success("点赞","点赞成功!");
        } else {
            //取消点赞
            redisTemplate.opsForHash().put(BehaviorConstants.LIKE_BEHAVIOR,likeBehaviorDTO.getArticleId().toString(),String.valueOf(likes - 1));

            //取消标记
            redisTemplate.delete(BehaviorConstants.LIKE_BEHAVIOR + "_" + likeBehaviorDTO.getArticleId());

            return Result.success("取消点赞","取消点赞成功");
        }
    }

    @Override
    public Result read(Long articleId) {
        if(articleId == null) {
            return Result.error("阅读行为error");
        }
        long views = 0L;
        //READ-BEHAVIOR : {articleId : 阅读数 }
        Object object = redisTemplate.opsForHash().get(BehaviorConstants.READ_BEHAVIOR, articleId.toString());
        if (object != null) {
            views = Long.parseLong(object.toString());
        }
        redisTemplate.opsForHash().put(BehaviorConstants.READ_BEHAVIOR,articleId.toString(), String.valueOf(views + 1));   //阅读数+1
        return Result.success();
    }

    @Override
    public Result collect(CollectBehaviorDTO collectBehaviorDTO) {
        if(collectBehaviorDTO.getArticleId() == null || collectBehaviorDTO.getOperation() == null) {
            return Result.error("参数异常");
        }

        User user = UserThreadLocalUtil.getUser();
        if(user == null || user.getId() == 0L) {
            return Result.error("需要登录");
        }

        long collects = 0L;
        //COLLECT-BEHAVIOR: {userId : collectBehaviorDTO}
        Object object = redisTemplate.opsForHash().get(BehaviorConstants.COLLECTION_BEHAVIOR, collectBehaviorDTO.getArticleId().toString());

        if(object != null) {
            collects = Long.parseLong(object.toString());
        }

        if(collectBehaviorDTO.getOperation() == 1) {
            //收藏文章行为
            //向redis保存当前key
            redisTemplate.opsForHash().put(BehaviorConstants.COLLECTION_BEHAVIOR ,collectBehaviorDTO.getArticleId().toString(),String.valueOf(collects + 1));
            //标记自己已收藏
            redisTemplate.opsForValue().set(BehaviorConstants.COLLECTION_BEHAVIOR + "_" + collectBehaviorDTO.getArticleId(),user.getId().toString());

            return Result.success("收藏","收藏成功!");
        } else  {
            //取消收藏
            redisTemplate.opsForHash().put(BehaviorConstants.COLLECTION_BEHAVIOR ,collectBehaviorDTO.getArticleId().toString(),String.valueOf(collects - 1));
            redisTemplate.delete(BehaviorConstants.COLLECTION_BEHAVIOR + "_" + collectBehaviorDTO.getArticleId());
            return Result.success("取消收藏","取消收藏成功");
        }

    }

    @Override
    public Result getData(Long articleId) {
        if(articleId == null) {
            return Result.error("参数异常");
        }
        BehaviorDataVO behaviorDataVO = new BehaviorDataVO();


        //获得阅读数
        Object viewObject = redisTemplate.opsForHash().get(BehaviorConstants.READ_BEHAVIOR, articleId.toString());

        //获得点赞数
        Object likeObject = redisTemplate.opsForHash().get(BehaviorConstants.LIKE_BEHAVIOR, articleId.toString());

        //获得收藏数
        Object collectObject = redisTemplate.opsForHash().get(BehaviorConstants.COLLECTION_BEHAVIOR, articleId.toString());

        if(viewObject == null || likeObject == null || collectObject == null) {
            ////从数据库获取文章并设置数据，并将数据存储到redis中
            ArticleDataDTO articleDataDTO = articleClient.getArticleDataById(articleId);
            behaviorDataVO.setViews(articleDataDTO.getViews());
            behaviorDataVO.setLikes(articleDataDTO.getLikes());
            behaviorDataVO.setCollects(articleDataDTO.getViews());

            //将数据存储到redis中
            redisTemplate.opsForHash().put(BehaviorConstants.LIKE_BEHAVIOR,articleId.toString(),articleDataDTO.getLikes().toString());
            redisTemplate.opsForHash().put(BehaviorConstants.COLLECTION_BEHAVIOR,articleId.toString(),articleDataDTO.getViews().toString());
            redisTemplate.opsForHash().put(BehaviorConstants.READ_BEHAVIOR,articleId.toString(),articleDataDTO.getViews().toString());
        } else {
            Integer views = Integer.parseInt(String.valueOf(viewObject));
            Integer likes = Integer.parseInt(String.valueOf(likeObject));
            Integer collects = Integer.parseInt(String.valueOf(collectObject));
            behaviorDataVO.setViews(views);
            behaviorDataVO.setCollects(collects);
            behaviorDataVO.setLikes(likes);
        }

        //判断自己是否点赞/收藏
        int likeMe = 0; //1 已点赞 0 未点赞
        int collectMe = 0;
        String likeStr = redisTemplate.opsForValue().get(BehaviorConstants.LIKE_BEHAVIOR + "_" + articleId);
        if(likeStr != null) {
            likeMe = 1;
        }
        String collectStr = redisTemplate.opsForValue().get(BehaviorConstants.COLLECTION_BEHAVIOR + "_" + articleId);
        if(collectStr != null) {
            collectMe = 1;
        }
        behaviorDataVO.setLikeMe(likeMe);
        behaviorDataVO.setCollectMe(collectMe);
        return Result.success(behaviorDataVO,"行为数据获取成功!");
    }


}
