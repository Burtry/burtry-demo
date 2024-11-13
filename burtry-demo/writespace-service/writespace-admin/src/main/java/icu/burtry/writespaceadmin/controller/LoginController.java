package icu.burtry.writespaceadmin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.IOException;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class LoginController {

    @GetMapping("/code")
    public void getCode(HttpServletResponse response) {

        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        // 生成验证码图片
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        try {
            lineCaptcha.setGenerator(randomGenerator);

            //TODO 将验证码存入Redis中方便验证

            // 设置响应类型为图片
            response.setContentType("image/png");
            lineCaptcha.write(response.getOutputStream());

            log.info("生成的验证码为:{}",lineCaptcha.getCode());

            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get")
    public String string() {
        return "Hello";
    }
}
