package icu.burtry.writespaceuserbehavior.controller;

import icu.burtry.writespacemodel.dto.CollectBehaviorDTO;
import icu.burtry.writespacemodel.dto.LikeBehaviorDTO;
import icu.burtry.writespaceuserbehavior.service.IUserBehaviorService;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/behavior")
@Slf4j
public class UserBehaviorController {

    @Autowired
    private IUserBehaviorService userBehaviorService;


    @PostMapping("/like")
    public Result like(@RequestBody LikeBehaviorDTO likeBehaviorDTO) {
        log.info("进行点赞:{}",likeBehaviorDTO);
        return userBehaviorService.like(likeBehaviorDTO);
    }

    @PostMapping("/collect")
    public Result collect(@RequestBody CollectBehaviorDTO collectBehaviorDTO) {
        log.info("收藏文章:{}",collectBehaviorDTO);
        return userBehaviorService.collect(collectBehaviorDTO);
    }

    @PostMapping("/read")
    public Result read(Long articleId) {
        log.info("阅读文章:{}",articleId);
        return userBehaviorService.read(articleId);
    }

    @GetMapping("/data")
    public Result getLikeNum(Long articleId) {
        log.info("获取行为数据:{}",articleId);
        return userBehaviorService.getData(articleId);
    }


}
