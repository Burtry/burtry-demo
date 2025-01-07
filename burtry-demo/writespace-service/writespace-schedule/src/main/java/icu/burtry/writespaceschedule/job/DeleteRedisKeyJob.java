package icu.burtry.writespaceschedule.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import icu.burtry.writespaceutils.constant.BehaviorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeleteRedisKeyJob {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //每天凌晨1点将READ-BEHAVIOR、LIKE-BEHAVIOR、COLLECTION-BEHAVIOR删除，防止内存被耗尽
    @XxlJob("writespaceDeleteRedisKeyScheduleHandler")
    public void deleteRedisKeyJobHandler() throws Exception {
        redisTemplate.delete(BehaviorConstants.READ_BEHAVIOR);
        redisTemplate.delete(BehaviorConstants.LIKE_BEHAVIOR);
        redisTemplate.delete(BehaviorConstants.COLLECTION_BEHAVIOR);
    }
}
