package icu.burtry.writespacemodel.vo;

import lombok.Data;

@Data
public class BehaviorDataVO {
    //点赞数
    private Integer likes;

    //阅读量
    private Integer views;

    //收藏数
    private Integer collects;

    //自己是否已点赞 1 已点赞 0 未点赞
    private Integer likeMe;

    //1已收藏 0 未收藏
    private Integer collectMe;
}
