package icu.burtry.writespaceadmin.controller;

import icu.burtry.writespaceadmin.service.ICommentService;
import icu.burtry.writespacemodel.dto.CommentPageQueryDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.entity.Comment;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/comment")
@Slf4j
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping()
    public Result<PageDTO<Comment>> getList(CommentPageQueryDTO pageQueryDTO) {
      log.info("根据文章id分页获取评论");
      return commentService.getList(pageQueryDTO);
    }

    @GetMapping("/{id}")
    public Result getOneById(@PathVariable("id") Long id) {
        log.info("根据id获取评论:{}",id);
        return commentService.getOneById(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        log.info("s删除评论:{}",id);
        return commentService.deleteById(id);
    }
}
