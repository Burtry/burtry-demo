package icu.burtry.writespaceuser.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import icu.burtry.writespacemodel.dto.UserRegisterDTO;
import icu.burtry.writespaceuser.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import icu.burtry.writespaceutils.result.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class LoginController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private LoginService loginService;


    @GetMapping("/code")
    public void getCode(HttpServletResponse response, String username) {

        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        // 生成验证码图片
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        try {
            lineCaptcha.setGenerator(randomGenerator);

            //TODO 将验证码存入Redis中方便验证
            redisTemplate.opsForValue().set("user_" + username + "_code",lineCaptcha.getCode(),120, TimeUnit.SECONDS);

            // 设置响应类型为图片
            response.setContentType("image/png");
            lineCaptcha.write(response.getOutputStream());

            log.info("生成的验证码为:{}",lineCaptcha.getCode());

            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return loginService.register(userRegisterDTO);
    }

    @GetMapping("/get")
    public Result get() {
        return Result.success("Hello-user", "获取成功");
    }

}
