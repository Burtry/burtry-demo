package icu.burtry.writespacearticle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.apis.user.IUserClient;
import icu.burtry.writespacearticle.config.RabbitMQConfig;
import icu.burtry.writespacearticle.mapper.ArticleConfigMapper;
import icu.burtry.writespacearticle.mapper.ArticleContentMapper;
import icu.burtry.writespacearticle.mapper.ArticleMapper;
import icu.burtry.writespacearticle.service.IArticleService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.dto.ArticleDataDTO;
import icu.burtry.writespacemodel.dto.ArticleLoadDTO;
import icu.burtry.writespacemodel.dto.ArticleVerifyMessageDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespacemodel.entity.article.ArticleConfig;
import icu.burtry.writespacemodel.entity.article.ArticleContent;
import icu.burtry.writespacemodel.vo.ArticleContentVO;
import icu.burtry.writespacemodel.vo.ArticleDetailVO;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespacemodel.vo.HotArticleVO;
import icu.burtry.writespaceutils.constant.ArticleStatusConstant;
import icu.burtry.writespaceutils.constant.ChannelConstant;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public Result saveArticle(ArticleDTO articleDTO) {

        if (articleDTO.hasNullOrEmptyFields()) {
            return Result.error("文章信息不完整，请检查后重试");
        }

        if(articleDTO.getId() != null) {
            updateArticle(articleDTO);
            return Result.success("文章修改成功!");
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
        article.setAuthorName(user.getNickName());

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
        article.setStatus(articleDTO.getStatus());

        if(articleDTO.getPublishTime() != null) {
            //将时间戳转换成LocalDateTime类型
            article.setPublishTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(articleDTO.getPublishTime()), ZoneId.systemDefault()));
        } else  {
            //立即发布,设置发布时间为now
            article.setPublishTime(LocalDateTime.now());
        }

        //保存文章
        save(article);
        //设置文章配置属性
        ArticleConfig articleConfig = new ArticleConfig();
        articleConfig.setArticleId(articleId);
        articleConfig.setIsDelete(0);   //未删除状态
        articleConfig.setIsComment(articleDTO.getCloseComment()? 1 : 0);    //0可评论 , 1关闭评论

        //保存文章配置
        articleConfigMapper.insert(articleConfig);

        //设置文章内容属性
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(articleId);
        articleContent.setContent(articleDTO.getContent());
        articleContentMapper.insert(articleContent);

        //如果为草稿，则直接返回
        if(article.getStatus() == 1) {
            return Result.success("草稿保存成功");
        }

        //异步进行敏感词过滤(如果包含敏感词此文章会被设置为已锁定)
        sendArticleVerifyMessage(articleId, articleDTO.getContent(), articleDTO.getTitle(),articleDTO.getPublishTime() != null);

        return Result.success("文章发布成功");
    }

    @Async
    public void sendArticleVerifyMessage(Long articleId, String content, String title,Boolean isScheduledPublish) {
        try {
            ArticleVerifyMessageDTO articleVerifyMessageDTO = new ArticleVerifyMessageDTO();
            articleVerifyMessageDTO.setArticleId(articleId);
            articleVerifyMessageDTO.setContent(content);
            articleVerifyMessageDTO.setTitle(title);
            if (isScheduledPublish) {
                articleVerifyMessageDTO.setIsScheduledPublish(true);
            }
            // 发送消息到 RabbitMQ
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, articleVerifyMessageDTO);
        } catch (Exception e) {
            // 异常处理
            log.error("发送文章验证消息失败，articleId: {}", articleId, e);
        }

    }

    private void updateArticle(ArticleDTO articleDTO) {

        //获取文章
        Article article = getById(articleDTO.getId());

        //更新文章
        article.setTitle(articleDTO.getTitle());
        article.setUpdateTime(LocalDateTime.now());
        article.setImages(articleDTO.getImages());
        article.setChannelId(articleDTO.getChannelId());
        //设置文章状态    (待审核状态)
        article.setStatus(articleDTO.getStatus());
        Channel channel = userClient.getChannelById(articleDTO.getChannelId());
        article.setChannelName(channel.getName());
        updateById(article);

        //更新文章配置
        ArticleConfig articleConfig = articleConfigMapper.selectOne(Wrappers.<ArticleConfig>lambdaQuery().eq(ArticleConfig::getArticleId, articleDTO.getId()));

        articleConfig.setIsComment(articleDTO.getCloseComment() ? 1 : 0);

        articleConfigMapper.updateById(articleConfig);

        //更新文章内容
        ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, articleDTO.getId()));

        articleContent.setContent(articleDTO.getContent());
        articleContentMapper.updateById(articleContent);

        //异步敏感词过滤
        sendArticleVerifyMessage(article.getId(), articleDTO.getContent(),articleDTO.getTitle(),articleDTO.getPublishTime() != null);
    }


    @Override
    public Result<List<ArticleVO>> getArticleList(Long id, Integer status) {
        //查询该用户信息
        User user = userClient.findUserById(id);

        if (BeanUtil.isEmpty(user)) {
            return Result.error("用户不存在，请重试!");
        }
        //根据用户id获取用户文章
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("author_id",id);

        //添加文章状态条件
        if(status != 0) {   //为0则查询全部文章。不为0则查询相关状态文章
            articleQueryWrapper.eq("status",status);
        }

        List<Article> list = list(articleQueryWrapper);

        //获取该用户未删除的文章
        list = articleMapper.getNoDeleteArticle(list.stream().map(Article::getId).collect(Collectors.toList()),id,status);

        //根据发布时间降序排序
        list = list.stream().sorted(Comparator.comparing(Article::getPublishTime).reversed()).collect(Collectors.toList());

        List<ArticleVO> articleVOList = new ArrayList<>();

        for (Article article : list) {
            ArticleVO articleVO = new ArticleVO();
            articleVO.setId(article.getId());
            articleVO.setTitle(article.getTitle());
            articleVO.setLikes(article.getLikes());
            articleVO.setViews(article.getViews());
            articleVO.setComments(article.getComments());
            articleVO.setUserAvatar(user.getImage());
            articleVO.setUsername(user.getNickName());
            articleVO.setImage(article.getImages());
            articleVO.setStatus(article.getStatus());
            articleVO.setChannelName(article.getChannelName());
            articleVO.setPublishTime(article.getPublishTime());
            articleVO.setUpdateTime(article.getUpdateTime());

            //设置文章内容

            ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, article.getId()));

            articleVO.setContent(articleContent.getContent());

            articleVOList.add(articleVO);

        }

        return Result.success(articleVOList,"用户文章获取成功");

    }

    @Override
    public Result deleteArticle(Long id) {
        User user = UserThreadLocalUtil.getUser();

        Article article = getById(id);
        if(article == null) {
            return Result.error("文章不存在!");
        }

        article.setStatus(6);   //6已删除
        updateById(article);

        if(user.getId() != article.getAuthorId()) {
            return Result.error("只能删除自己的文章!");
        }

        //删除文章,将文章配置中的is_delete设置为1即为删除
        ArticleConfig articleConfig = articleConfigMapper.selectOne(Wrappers.<ArticleConfig>lambdaQuery().eq(ArticleConfig::getArticleId, id));

        if(articleConfig == null) {
            return Result.error("文章信息错误，删除失败，请联系管理员!");
        }

        articleConfig.setIsDelete(1);

        articleConfigMapper.updateById(articleConfig);

        try {
            if(article.getId() != null) {
                restHighLevelClient.delete(new DeleteRequest("article_info",article.getId().toString()), RequestOptions.DEFAULT);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.success("删除成功!");

    }

    @Override
    public Result overView(Long id) {
        User user = userClient.findUserById(id);
        if(user == null) {
            Result.error("用户信息错误!");
        }
        //获取该用户文章的总点赞量，总收藏量，总评论量，总阅读量
        //根据用户id获取用户文章
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("author_id",id);
        List<Article> list = list(articleQueryWrapper);
        //获取该用户未删除的文章
        list = articleMapper.getNoDeleteArticle(list.stream().map(article -> article.getId()).collect(Collectors.toList()),id,4);   //此处的4为所有已发布文章的文章列表

        Integer sumLikes = list.stream().mapToInt(Article::getLikes).sum();
        Integer sumCollects = list.stream().mapToInt(Article::getCollections).sum();
        Integer sumComments = list.stream().mapToInt(Article::getComments).sum();
        Integer sumViews = list.stream().mapToInt(Article::getViews).sum();

        HashMap<String, Integer> map = new HashMap<>();
        map.put("sumLikes", sumLikes);
        map.put("sumCollects", sumCollects);
        map.put("sumComments", sumComments);
        map.put("sumViews", sumViews);

        return Result.success(map,"文章数据总览获取成功!");
    }

    @Override
    public Result<ArticleContentVO> getArticleVOById(Long id) {
        if(id == null) {
            return Result.error("ID信息错误!");
        }
        ArticleContentVO articleContentVO = new ArticleContentVO();
        Article article = getById(id);
        if(article == null) {
            return Result.error("文章不存在!");
        }
        articleContentVO.setImage(article.getImages());
        articleContentVO.setTitle(article.getTitle());
        articleContentVO.setId(article.getId());
        articleContentVO.setChannelId(article.getChannelId());

        //获取文章内容
        ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, id));

        articleContentVO.setContent(articleContent.getContent());

        //获取文章配置
        ArticleConfig articleConfig = articleConfigMapper.selectOne(Wrappers.<ArticleConfig>lambdaQuery().eq(ArticleConfig::getArticleId, id));

        articleContentVO.setIsComment(articleConfig.getIsComment());


        return Result.success(articleContentVO,"获取成功!");

    }

    @Override
    public Result<ArticleDetailVO> getArticleDetailById(Long id) {
        if(id == null) {
            return Result.error("ID信息错误!");
        }
        ArticleDetailVO articleDetailVO = new ArticleDetailVO();
        Article article = getById(id);
        if(article == null) {
            return Result.error("文章不存在!");
        }

        articleDetailVO.setId(article.getId());
        articleDetailVO.setTitle(article.getTitle());

        //获取用户头像
        //根据用户id查询用户
        User user = userClient.findUserById(article.getAuthorId());
        if(user == null) {
            return Result.error("作者不存在!");
        }

        articleDetailVO.setUserAvatar(user.getImage());
        articleDetailVO.setUsername(user.getNickName());
        articleDetailVO.setUserId(user.getId());
        articleDetailVO.setUpdateTime(article.getUpdateTime());

        //获取文章内容
        ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, id));

        articleDetailVO.setContent(articleContent.getContent());

        return Result.success(articleDetailVO,"获取成功!");
    }

    @Override
    public Result<List<ArticleVO>> load(ArticleLoadDTO articleLoadDTO) {
        if (BeanUtil.isEmpty(articleLoadDTO)) {
            return Result.error("请求参数异常，请重试!");
        }

        if(articleLoadDTO.getChannelId() == 1) {
            //从redis 中获得 推荐文章
            String string = redisTemplate.opsForValue().get("hotArticleList");

            List<HotArticleVO> hotArticleVOList = JSON.parseArray(string, HotArticleVO.class);

            if(hotArticleVOList == null || hotArticleVOList.isEmpty()) {
                return Result.error("暂无推荐文章");
            }

            List<ArticleVO> result = new ArrayList<>();
            for (HotArticleVO hotArticleVO : hotArticleVOList) {
                ArticleVO articleVO = new ArticleVO();
                BeanUtils.copyProperties(hotArticleVO,articleVO);
                result.add(articleVO);
            }
            return Result.success(result,"获得推荐文章成功");
        }

        Page<Article> page = new Page<>(articleLoadDTO.getPageNum(), articleLoadDTO.getPageSize());

        //按发布时间倒序排序查询10条文章、频道筛选、已发布文章且未删除的文章
        IPage<Article> articlePage = articleMapper.loadArticles(page, articleLoadDTO.getChannelId());

        List<Article> articleList = articlePage.getRecords();

        ArrayList<ArticleVO> articleVOS = new ArrayList<>();

        for (Article article : articleList) {
            ArticleVO articleVO = new ArticleVO();
            //获取每个文章内容
            ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, article.getId()));
            if(articleContent != null) {
                //设置文章内容
                articleVO.setContent(articleContent.getContent());
            } else {
                articleVO.setContent("获取文章内容错误!");
            }

            User user = userClient.findUserById(article.getAuthorId());
            //属性设置
            articleVO.setId(article.getId());
            articleVO.setTitle(article.getTitle());
            articleVO.setLikes(article.getLikes());
            articleVO.setViews(article.getViews());
            articleVO.setComments(article.getComments());
            articleVO.setUserAvatar(user.getImage());
            articleVO.setUsername(user.getNickName());
            articleVO.setUserId(user.getId());
            articleVO.setImage(article.getImages());
            articleVO.setStatus(article.getStatus());
            articleVO.setChannelName(article.getChannelName());
            articleVO.setPublishTime(article.getPublishTime());
            articleVO.setUpdateTime(article.getUpdateTime());
            articleVOS.add(articleVO);
        }
        return Result.success(articleVOS,"获取成功！");
    }

    @Override
    public void postData(Map<Long,ArticleDataDTO> map) {
        Collection<ArticleDataDTO> values = map.values();
        List<Article> articles = values.stream()
                .map(dto -> {
                    Article article = new Article();
                    article.setId(dto.getArticleId());
                    article.setLikes(dto.getLikes());
                    article.setViews(dto.getViews());
                    article.setCollections(dto.getCollects());
                    return article;
                })
                .collect(Collectors.toList());

        //批量添加或更新
        saveOrUpdateBatch(articles);
    }


}
