package icu.burtry.writespaceuser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import icu.burtry.writespaceutils.result.Result;
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @GetMapping("/get")
    public Result get() {
        return Result.success("Hello-user", "获取成功");
    }

}
