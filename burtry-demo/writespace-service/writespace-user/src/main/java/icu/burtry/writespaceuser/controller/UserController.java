package icu.burtry.writespaceuser.controller;

import icu.burtry.writespacemodel.dto.UpdateUserInfoDTO;
import icu.burtry.writespaceuser.service.UserService;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/info")
    public Result updateUserInfo(@RequestBody UpdateUserInfoDTO userInfoDTO) {
        userService.updateInfo(userInfoDTO);
        return Result.success();
    }


}
