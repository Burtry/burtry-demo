package icu.burtry.writespaceutils.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertToLocalDateTimeUtil {

    /**
     * 将字符串转换为 LocalDateTime
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime 对象
     */
    public static LocalDateTime convertToLocalDateTime(String dateTimeStr) {
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
