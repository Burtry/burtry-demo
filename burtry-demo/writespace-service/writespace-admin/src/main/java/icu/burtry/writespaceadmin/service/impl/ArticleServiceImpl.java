package icu.burtry.writespaceadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.ArticleMapper;
import icu.burtry.writespaceadmin.mapper.ChannelMapper;
import icu.burtry.writespaceadmin.service.IArticleService;
import icu.burtry.writespaceadmin.service.IUserService;
import icu.burtry.writespacemodel.dto.AdminArticleSearchDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.vo.ArticleDetailVO;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.utils.ConvertToLocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private IUserService userService;

    @Override
    public Result<List<Channel>> getChannelList() {
        QueryWrapper<Channel> channelQueryWrapper = new QueryWrapper<>();
        List<Channel> channels = channelMapper.selectList(channelQueryWrapper);
        return Result.success(channels,"获取成功!");
    }


    @Override
    public Result<PageDTO<Article>> getArticleList(AdminArticleSearchDTO adminArticleSearchDTO) {

        Page<Article> page = new Page<>(adminArticleSearchDTO.getPageNum(), adminArticleSearchDTO.getPageSize());

        //添加获取列表条件
        QueryWrapper<Article> articleQueryWrapper = getArticleQueryWrapper(adminArticleSearchDTO);
        page = articleMapper.selectPage(page,articleQueryWrapper);
        List<Article> articleList = page.getRecords();

        if (articleList.isEmpty()) {
            return Result.success(new PageDTO<>(page.getTotal(),page.getPages(),articleList),"搜索为空");
        }else  {
            return Result.success(new PageDTO<>(page.getTotal(),page.getPages(),articleList),"获取成功");
        }

    }

    @Override
    public Result<ArticleDetailVO> getDetail(Long id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        Article article = getById(id);
        if(article == null) {
            return Result.error("文章不存在");
        }
        ArticleDetailVO articleDetailVO = new ArticleDetailVO();

        //获得文章内容
        String content = articleMapper.getContent(id);

        articleDetailVO.setId(article.getId());
        articleDetailVO.setTitle(article.getTitle());
        articleDetailVO.setContent(content);
        articleDetailVO.setUpdateTime(article.getUpdateTime());
        articleDetailVO.setUserId(article.getAuthorId());
        articleDetailVO.setUsername(article.getAuthorName());
        articleDetailVO.setStatus(article.getStatus());

        User user = userService.getById(article.getAuthorId());
        if(user != null) {
            articleDetailVO.setUserAvatar(user.getImage());
        }

        return Result.success(articleDetailVO,"获取成功");

    }

    @Override
    public Result updateStatus(Long id, Integer status) {
        if(id == null || status == null) {
            return Result.error("参数异常");
        }
        Article article = getById(id);
        if(article == null) {
            return Result.error("文章不存在");
        }
        if(status == 6 && article.getStatus() == 4) {
            //删除文章，将文章配置表中设置为删除文章
            articleMapper.deleteArticle(id);
            article.setStatus(status);
            updateById(article);
            //TODO es中删除该文章


        } else {
            article.setStatus(status);
            updateById(article);
        }


        return Result.success();
    }

    private static QueryWrapper<Article> getArticleQueryWrapper(AdminArticleSearchDTO adminArticleSearchDTO) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();

        if (adminArticleSearchDTO.getStatus() != 0) {
            articleQueryWrapper.eq("status", adminArticleSearchDTO.getStatus());
        }

        if (adminArticleSearchDTO.getChannelId() != null) {
            articleQueryWrapper.eq("channel_id", adminArticleSearchDTO.getChannelId());
        }
        if(adminArticleSearchDTO.getAuthorId() != null) {
            articleQueryWrapper.eq("author_id", adminArticleSearchDTO.getAuthorId());
        }

        if (adminArticleSearchDTO.getArticleId() != null) {
            articleQueryWrapper.eq("id",adminArticleSearchDTO.getArticleId());
        }

        if (adminArticleSearchDTO.getStartDateTime() != null && adminArticleSearchDTO.getEndDateTime() != null) {
            LocalDateTime startTime = ConvertToLocalDateTimeUtil.convertToLocalDateTime(adminArticleSearchDTO.getStartDateTime());
            LocalDateTime endTime = ConvertToLocalDateTimeUtil.convertToLocalDateTime(adminArticleSearchDTO.getEndDateTime());
            articleQueryWrapper.ge("create_time",startTime).le("create_time",endTime);
        }

        //不包含草稿文章
        articleQueryWrapper.ne("status",1);

        //根据创建时间倒序排序
        articleQueryWrapper.orderByDesc("create_time");
        return articleQueryWrapper;
    }


}
