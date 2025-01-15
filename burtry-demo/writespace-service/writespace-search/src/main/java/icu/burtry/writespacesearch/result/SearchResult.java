package icu.burtry.writespacesearch.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class SearchResult<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;
    private Long total;
    private Integer pageNum;
    private Integer pageSize;

    public static <T> SearchResult<T> success(T object, String msg,Long total,Integer pageNum, Integer pageSize) {
        SearchResult<T> result = new SearchResult<T>();
        result.data = object;
        result.code = 1;
        result.msg = msg;
        result.total = total;
        result.pageNum = pageNum;
        result.pageSize = pageSize;
        return result;
    }

    public static <T> SearchResult<T> error(String msg) {
        SearchResult result = new SearchResult();
        result.msg = msg;
        result.code = 0;
        return result;
    }


}
