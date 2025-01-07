package icu.burtry.writespaceuserbehavior.mapper;

import icu.burtry.writespacemodel.entity.UserCollection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserCollectMapper {
    @Select("select * from writespace.user_collection where user_id = #{id} and article_id = #{articleId}")
    UserCollection selectOneByUserIdAndArticleId(Long id, Long articleId);

    @Delete("delete from writespace.user_collection where user_id = #{id} and article_id =  #{articleId}")
    void delete(Long id, Long articleId);
}
