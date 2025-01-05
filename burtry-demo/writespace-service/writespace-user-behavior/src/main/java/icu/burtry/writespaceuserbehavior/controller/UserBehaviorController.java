package icu.burtry.writespaceuserbehavior.controller;

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

    @GetMapping("/like")
    public Result getLikeNum(Long articleId) {
        log.info("获取点赞数据");
        return userBehaviorService.getLikes(articleId);
    }
}
