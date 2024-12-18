package icu.burtry.writespaceuser.controller;

import icu.burtry.writespacemodel.dto.UpdateUserInfoDTO;
import icu.burtry.writespaceuser.service.UserService;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public Result getUserById(@PathVariable Long id) {
        log.info("根据用户id查询用户:{}",id);
        return userService.getUserInfo(id);
    }


}
