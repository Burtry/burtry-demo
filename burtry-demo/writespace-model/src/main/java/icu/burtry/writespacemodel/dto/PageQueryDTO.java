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
     * 根据sortBy排序
     */
    private String sortBy;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 频道状态 0全部  1已启用状态  2以禁用状态
     */
    private Integer status;
}
