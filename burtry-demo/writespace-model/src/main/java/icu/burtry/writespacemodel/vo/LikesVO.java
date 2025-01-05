package icu.burtry.writespacemodel.vo;

import lombok.Data;

@Data
public class LikesVO {
    //点赞数
    private Integer likes;

    //自己是否已点赞 1 已点赞 0 未点赞
    private Integer likeMe;
}
