package icu.burtry.writespaceadmin.controller;



import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import icu.burtry.writespaceadmin.service.ILoginService;
import icu.burtry.apis.user.IUserClient;
import icu.burtry.writespacemodel.dto.AdminLoginDTO;
import icu.burtry.writespacemodel.dto.AdminRegisterDTO;


import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private IUserClient userClient;



    @GetMapping("/code")
    public void getCode(HttpServletResponse response, String username) {

        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        // 生成验证码图片
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        try {
            lineCaptcha.setGenerator(randomGenerator);

            //TODO 将验证码存入Redis中方便验证
            redisTemplate.opsForValue().set(username,lineCaptcha.getCode(),120, TimeUnit.SECONDS);

            // 设置响应类型为图片
            response.setContentType("image/png");
            lineCaptcha.write(response.getOutputStream());

            log.info("生成的验证码为:{}",lineCaptcha.getCode());

            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/login")
    public Result login(AdminLoginDTO adminLoginDTO) {
        log.info("管理员登录:{}",adminLoginDTO);
        return loginService.login(adminLoginDTO);

    }

    @PostMapping("/register")
    public Result register(@RequestBody AdminRegisterDTO adminRegisterDTO) {
        log.info("管理员注册:{}",adminRegisterDTO);
        return loginService.register(adminRegisterDTO);
    }

    @GetMapping("/get")
    public Result getString() {
        User user = userClient.findUserById(1L);
        return Result.success("Hello" + user, "获取成功");
    }
}








