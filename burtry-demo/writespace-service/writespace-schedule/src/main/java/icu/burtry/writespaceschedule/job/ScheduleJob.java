package icu.burtry.writespaceschedule.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ScheduleJob {

    @Autowired
    private RedisTemplate redisTemplate;

    @XxlJob("writespaceScheduleHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("执行用户行为数据刷新到数据库定时任务...");

        //从redis中获得阅读数
        //redisTemplate.opsForValue().get(Be)

    }

}
