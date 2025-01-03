package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class ArticleLoadDTO {

    private Long channelId;

    private Integer pageNum = 1;   //当前页

    private Integer pageSize = 10;  //每页十条数据


}
