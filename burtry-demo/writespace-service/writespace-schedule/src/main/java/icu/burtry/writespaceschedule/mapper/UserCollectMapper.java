package icu.burtry.writespaceschedule.mapper;

import icu.burtry.writespacemodel.entity.UserLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface UserCollectMapper {
    /**
     * 批量插入收藏数据
     * @param collectList 收藏数据列表
     */
    void batchInsertCollects(@Param("collectList") List<UserLike> collectList);
}
