package icu.burtry.writespaceadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.CommentMapper;
import icu.burtry.writespaceadmin.service.ICommentService;
import icu.burtry.writespacemodel.dto.CommentPageQueryDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.entity.Comment;
import icu.burtry.writespaceutils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result<PageDTO<Comment>> getList(CommentPageQueryDTO pageQueryDTO) {
        if (BeanUtil.isEmpty(pageQueryDTO)) {
            //参数异常
            return Result.error("参数异常，请重试!");
        }

        Page<Comment> page = new Page<>(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();

        if(pageQueryDTO.getArticleId() != null) {
            commentQueryWrapper.eq("article_id",pageQueryDTO.getArticleId());
        }

        //根据创建时间倒序排序
        commentQueryWrapper.orderByDesc("create_time");


        page = commentMapper.selectPage(page,commentQueryWrapper);

        List<Comment> commentList = page.getRecords();

        if (commentList.isEmpty()) {
            return Result.success(new PageDTO<>(page.getTotal(),page.getPages(), Collections.emptyList()),"暂无评论!");
        }else {
            return Result.success(new PageDTO<>(page.getTotal(),page.getPages(),commentList),"获取成功!");
        }

    }

    @Override
    public Result getOneById(Long id) {
        if(id == null) {
            return Result.error("参数异常");
        }
        Comment comment = getById(id);
        if (comment == null) {
            return Result.error("评论不存在!");
        }

        return Result.success(comment,"获取成功");
    }

    @Override
    public Result deleteById(Long id) {
        if(id == null) {
            return Result.error("参数异常");
        }
        removeById(id);
        return Result.success();
    }
}
