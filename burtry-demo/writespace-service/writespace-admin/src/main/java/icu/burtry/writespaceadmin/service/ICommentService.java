package icu.burtry.writespaceadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.CommentPageQueryDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.entity.Comment;
import icu.burtry.writespaceutils.result.Result;

public interface ICommentService extends IService<Comment> {

    /**
     * 分页获取评论列表
     * @param pageQueryDTO
     * @return
     */
    Result<PageDTO<Comment>> getList(CommentPageQueryDTO pageQueryDTO);

    Result getOneById(Long id);

    Result deleteById(Long id);
}
