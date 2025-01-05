package icu.burtry.writespaceuserbehavior.service;

import icu.burtry.writespacemodel.dto.LikeBehaviorDTO;
import icu.burtry.writespaceutils.result.Result;

public interface IUserBehaviorService {
    /**
     * 用户点赞行为
     * @param likeBehaviorDTO
     */
    Result like(LikeBehaviorDTO likeBehaviorDTO);

    /**
     * 获得点赞数据
     * @return
     */
    Result getData(Long articleId);

    /**
     * 阅读文章
     * @param articleId
     * @return
     */
    Result read(Long articleId);
}
