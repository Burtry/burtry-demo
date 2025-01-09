package icu.burtry.writespacecomment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.burtry.writespacemodel.entity.ArticleComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommentMapper extends BaseMapper<ArticleComment> {

    @Update("update writespace.article set comments = comments + 1 where id = #{articleId}")
    void commentNumsAdd(Long articleId);
}
