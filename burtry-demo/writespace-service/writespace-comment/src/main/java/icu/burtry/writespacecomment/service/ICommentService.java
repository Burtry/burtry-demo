package icu.burtry.writespacecomment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.CommentDTO;
import icu.burtry.writespacemodel.entity.ArticleComment;
import icu.burtry.writespacemodel.vo.CommentVO;
import icu.burtry.writespaceutils.result.Result;

import java.util.List;

public interface ICommentService extends IService<ArticleComment> {

    /**
     * 保存评论
     * @param commentDTO
     * @return
     */
    Result saveComment(CommentDTO commentDTO);

    Result<List<CommentVO>> getListByArticleId(Long articleId);
}
