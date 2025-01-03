package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class PageQueryDTO {
    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 页面数
     */
    private Integer pageSize;

    /**
     * 关键字
     */
    private String keyword;


    /**
     * 根据sortBy排序
     */
    private String sortBy;

}

