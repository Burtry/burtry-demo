package icu.burtry.writespacemodel.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sensitization {

    private Long id;

    private String sensitiveName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
