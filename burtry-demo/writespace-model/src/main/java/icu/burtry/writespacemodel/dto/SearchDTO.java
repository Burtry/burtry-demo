package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class SearchDTO {

    private String keyWords;

    private Integer pageNum;

    private Integer pageSize;

}
