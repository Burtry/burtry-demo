package icu.burtry.writespacemodel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Channel {
    //频道id
    @TableId(type = IdType.AUTO)
    private Long id;

    //频道名称
    private String name;

    //频道描述
    private String description;

    //频道状态 0 可用 ;1 不可用
    private Integer status;

    //创建时间
    private LocalDateTime createTime;
}
