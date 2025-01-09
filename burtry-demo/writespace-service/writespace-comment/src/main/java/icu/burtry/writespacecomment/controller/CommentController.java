package icu.burtry.writespacecomment.controller;

import icu.burtry.writespacecomment.service.ICommentService;
import icu.burtry.writespacemodel.dto.CommentDTO;
import icu.burtry.writespacemodel.vo.CommentVO;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;


    @PostMapping()
    public Result save(@RequestBody CommentDTO commentDTO) {
        log.info("保存评论:{}",commentDTO);
        return commentService.saveComment(commentDTO);
    }

    @GetMapping()
    public Result<List<CommentVO>> getCommentList(Long articleId) {
        log.info("获取评论列表:{}",articleId);
        return commentService.getListByArticleId(articleId);
    }

}
