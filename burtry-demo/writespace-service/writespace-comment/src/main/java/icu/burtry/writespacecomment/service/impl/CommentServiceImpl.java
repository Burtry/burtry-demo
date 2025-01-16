package icu.burtry.writespacecomment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.apis.user.IUserClient;
import icu.burtry.writespacecomment.mapper.ArticleMapper;
import icu.burtry.writespacecomment.mapper.CommentMapper;
import icu.burtry.writespacecomment.service.ICommentService;
import icu.burtry.writespacemodel.dto.CommentDTO;
import icu.burtry.writespacemodel.entity.ArticleComment;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.entity.article.ArticleConfig;
import icu.burtry.writespacemodel.vo.CommentVO;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, ArticleComment> implements ICommentService {

    @Autowired
    private IUserClient userClient;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result saveComment(CommentDTO commentDTO) {
        if(BeanUtil.isEmpty(commentDTO)) {
            return Result.error("参数错误，请重试!");
        }

        if(commentDTO.getUserId() == 0L) {
            return Result.error("请先登录");
        }

        if(commentDTO.getContent().isEmpty()) {
            return Result.error("评论不能为空!");
        }

        //获得文章配置表
        ArticleConfig articleConfig = articleMapper.getArticleById(commentDTO.getArticleId());
        if (articleConfig.getIsComment() == 1) {
            return Result.error("作者设置文章不可评论");
        }
        ArticleComment articleComment = new ArticleComment();
        BeanUtils.copyProperties(commentDTO,articleComment);
        articleComment.setCreateTime(LocalDateTime.now());
        save(articleComment);

        //文章评论数加一
        commentMapper.commentNumsAdd(commentDTO.getArticleId());
        return Result.success();
    }

    @Override
    public Result<List<CommentVO>> getListByArticleId(Long articleId) {
        if(articleId == null) {
            return Result.error("文章信息错误!");
        }
        List<ArticleComment> articleCommentList = list(Wrappers.<ArticleComment>lambdaQuery().eq(ArticleComment::getArticleId, articleId));

        ArrayList<CommentVO> commentVOS = new ArrayList<>();

        for(ArticleComment articleComment: articleCommentList) {
            User user = userClient.findUserById(articleComment.getUserId());
            if(user != null) {
                CommentVO commentVO = new CommentVO();
                commentVO.setUserId(user.getId());
                commentVO.setUserAvatar(user.getImage());
                commentVO.setUsername(user.getNickName());
                commentVO.setPublishTime(articleComment.getCreateTime());
                commentVO.setContent(articleComment.getContent());
                commentVOS.add(commentVO);
            }
        }

        return Result.success(commentVOS,"获取成功！");

    }
}
