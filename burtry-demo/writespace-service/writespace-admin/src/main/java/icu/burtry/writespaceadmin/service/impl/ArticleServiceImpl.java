package icu.burtry.writespaceadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.ArticleMapper;
import icu.burtry.writespaceadmin.mapper.ChannelMapper;
import icu.burtry.writespaceadmin.service.IArticleService;
import icu.burtry.writespacemodel.dto.AdminArticleSearchDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.utils.ConvertToLocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private ArticleMapper articleMapper;

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
