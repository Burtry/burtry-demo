package icu.burtry.writespaceadmin.globalException;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import icu.burtry.writespaceutils.result.Result;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * get请求中的参数校验
     * @param
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Map<String, String>> handleValidationExceptions(ConstraintViolationException ex) {
        return Result.error(ex.getMessage());
    }

    /**
     * form-data格式的参数校验
     * @param
     * @return
     */

    @ExceptionHandler(BindException.class)
    public Result<Map<String, String>> bindExceptionHandler(BindException ex) {
        return Result.error(ex.getMessage());
    }

    /**
     * json格式的参数校验
     * @param
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<Map<String, String>> Exceptions(Exception ex) {
        // 使用 Result 封装错误信息
        return Result.error("系统异常");
    }

}
