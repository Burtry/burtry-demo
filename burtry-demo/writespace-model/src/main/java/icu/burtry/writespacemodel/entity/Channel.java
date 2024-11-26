package icu.burtry.writespacemodel.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Channel {
    //频道id
    private Long id;

    //频道名称
    private String name;

    //频道描述
    private String description;

    //频道状态 0 可用 ;1 不可用
    private Integer status;

    //是否默认
    private Integer isDefault;

    //创建时间
    private LocalDateTime createTime;
}
