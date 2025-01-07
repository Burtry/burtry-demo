package icu.burtry.writespacemodel.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCollection {

    private Long id;

    private Long userId;

    private Long articleId;

    private LocalDateTime createTime;

}
