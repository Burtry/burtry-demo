package icu.burtry.writespacemodel.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLike {
    private Long id;

    private Long userId;

    private Long articleId;

    private LocalDateTime createTime;

}
