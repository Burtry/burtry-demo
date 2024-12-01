package icu.burtry.writespaceadmin.globalException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import icu.burtry.writespaceutils.result.Result;


import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result<Map<String, String>> Exceptions(Exception ex) {
        log.info("出现异常:{}", ex.getMessage());
        log.error(ex.getMessage());
        // 使用 Result 封装错误信息
        return Result.error("服务异常");
    }

}
