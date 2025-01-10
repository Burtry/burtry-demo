package icu.burtry.writespacemodel.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ArticleVerifyMessageDTO implements Serializable {

    private Long articleId;

    private String title;

    private String content;

    //是否定时发布
    private Boolean isScheduledPublish = false;

}
