package icu.burtry.writespacemodel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("article_comment")
public class Comment {

    private Long id;

    private Long articleId;

    private Long userId;

    private String content;

    private LocalDateTime createTime;

}
