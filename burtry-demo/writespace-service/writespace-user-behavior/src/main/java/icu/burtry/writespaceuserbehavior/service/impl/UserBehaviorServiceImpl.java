package icu.burtry.writespaceuserbehavior.service.impl;

import com.alibaba.fastjson.JSON;
import icu.burtry.writespacemodel.dto.LikeBehaviorDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.vo.LikesVO;
import icu.burtry.writespaceuserbehavior.service.IUserBehaviorService;
import icu.burtry.writespaceutils.constant.BehaviorConstants;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.thread.UserThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserBehaviorServiceImpl implements IUserBehaviorService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public Result like(LikeBehaviorDTO likeBehaviorDTO) {

        if (likeBehaviorDTO.getArticleId() == null || likeBehaviorDTO.getOperation() == null) {
            return Result.error("参数错误!");
        }

        User user = UserThreadLocalUtil.getUser();
        if(user == null || user.getId() == 0L) {
            return Result.error("需要登录!");
        }

        if(likeBehaviorDTO.getOperation() == 1) {
            //点赞            //LIKE-BEHAVIOR-articleId : userId
            Object object = redisTemplate.opsForHash().get(BehaviorConstants.LIKE_BEHAVIOR + likeBehaviorDTO.getArticleId().toString(), user.getId().toString());

            if(object != null) {
                return Result.success("已点赞","已点赞");
            }

            //向redis保存当前key
            redisTemplate.opsForHash().put(BehaviorConstants.LIKE_BEHAVIOR + likeBehaviorDTO.getArticleId(),user.getId().toString(), JSON.toJSONString(likeBehaviorDTO));

            return Result.success("点赞","点赞成功!");
        } else {
            //取消点赞
            redisTemplate.opsForHash().delete(BehaviorConstants.LIKE_BEHAVIOR + likeBehaviorDTO.getArticleId(),user.getId().toString());

            return Result.success("取消点赞","取消点赞成功");
        }
    }

    @Override
    public Result getLikes(Long articleId) {
        if(articleId == null) {
            return Result.error("参数异常");
        }

        //首先从缓存中获取，获取不到再向数据库中获取
        Long likes = redisTemplate.opsForHash().size(BehaviorConstants.LIKE_BEHAVIOR + articleId);

        LikesVO likesVO = new LikesVO();
        likesVO.setLikes(Math.toIntExact(likes));
        //判断自己是否点赞
        User user = UserThreadLocalUtil.getUser();
        Object object = redisTemplate.opsForHash().get(BehaviorConstants.LIKE_BEHAVIOR + articleId, user.getId().toString());

        if(object != null) {
            //自己已点赞
            likesVO.setLikeMe(1);
        }

        return Result.success(likesVO,"行为数据获取成功!");
    }
}
