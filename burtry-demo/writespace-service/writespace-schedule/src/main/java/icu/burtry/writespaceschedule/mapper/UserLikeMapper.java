package icu.burtry.writespaceschedule.mapper;

import icu.burtry.writespacemodel.entity.UserLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface UserLikeMapper {
    /**
     * 批量插入点赞数据
     * @param likeList 点赞数据列表
     */
    void batchInsertLikes(@Param("likeList") List<UserLike> likeList);
}
