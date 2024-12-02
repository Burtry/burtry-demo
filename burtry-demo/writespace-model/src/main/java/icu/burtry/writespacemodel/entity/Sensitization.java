package icu.burtry.writespacemodel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sensitization {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String sensitiveName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
