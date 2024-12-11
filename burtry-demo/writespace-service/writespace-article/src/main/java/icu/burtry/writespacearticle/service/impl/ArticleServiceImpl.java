package icu.burtry.writespacearticle.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.apis.user.IUserClient;
import icu.burtry.writespacearticle.mapper.ArticleConfigMapper;
import icu.burtry.writespacearticle.mapper.ArticleContentMapper;
import icu.burtry.writespacearticle.mapper.ArticleMapper;
import icu.burtry.writespacearticle.service.IArticleService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.entity.article.ArticleConfig;
import icu.burtry.writespacemodel.entity.article.ArticleContent;
import icu.burtry.writespaceutils.constant.ArticleStatusConstant;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleContentMapper articleContentMapper;

    @Autowired
    private ArticleConfigMapper articleConfigMapper;

    @Autowired
    private IUserClient userClient;


    @Override
    public Result saveArticle(ArticleDTO articleDTO) {

        if (articleDTO.hasNullOrEmptyFields()) {
            return Result.error("文章信息不完整，请检查后重试");
        }

        //此user只包含userId
        User midUser = UserThreadLocalUtil.getUser();
        //获取用户信息
        User user = userClient.findUserById(midUser.getId());

        if(user == null) {
            return Result.error("用户信息错误，请重试");
        }

        //设置文章属性
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setAuthorId(user.getId());
        article.setAuthorName(user.getName());

        Channel channel = userClient.getChannelById(articleDTO.getChannelId());
        if(channel == null) {
            return Result.error("频道信息错误，请重试");
        }

        //生成id
        long articleId = IdUtil.getSnowflake(1, 1).nextId();
        article.setId(articleId);
        article.setChannelId(articleDTO.getChannelId());
        article.setChannelName(channel.getName());
        article.setImages(articleDTO.getImages());
        article.setLikes(0);
        article.setViews(0);
        article.setCollections(0);
        article.setComments(0);
        article.setReports(0);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        //立即发布,设置发布时间为now
        article.setPublishTime(LocalDateTime.now());
        //设置文章状态    (待审核状态)
        article.setStatus(ArticleStatusConstant.AWAITING_REVIEW);

        //TODO 审核 异步审核 ,计划发送消息到消息队列进行审核


        //审核成功保存文章
        save(article);


        //设置文章配置属性
        ArticleConfig articleConfig = new ArticleConfig();
        articleConfig.setArticleId(articleId);
        articleConfig.setIsDelete(0);   //未删除状态
        articleConfig.setIsComment(articleDTO.getCloseComment()? 0 : 1);    //0可评论 , 1关闭评论

        //保存文章配置
        articleConfigMapper.insert(articleConfig);

        //设置文章内容属性
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(articleId);
        articleContent.setContent(articleDTO.getContent());
        articleContentMapper.insert(articleContent);

        return Result.success("文章发布成功!");
    }
}
