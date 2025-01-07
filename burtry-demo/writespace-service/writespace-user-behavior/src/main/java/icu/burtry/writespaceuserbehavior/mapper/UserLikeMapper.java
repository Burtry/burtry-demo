package icu.burtry.writespaceuserbehavior.mapper;

import icu.burtry.writespacemodel.entity.UserLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserLikeMapper {

    @Select("select * from writespace.user_like where user_id = #{id} and article_id = #{articleId}")
    UserLike selectOneByUserIdAndArticleId(Long id, Long articleId);

    @Delete("delete from writespace.user_like where user_id = #{id} and article_id = #{articleId}")
    void delete(Long id, Long articleId);
}
