package icu.burtry.writespacearticle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import icu.burtry.writespacemodel.vo.ArticleContentVO;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespaceutils.constant.ArticleStatusConstant;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.thread.UserThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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


    @Override
    public Result saveArticle(ArticleDTO articleDTO) {

        if (articleDTO.hasNullOrEmptyFields()) {
            return Result.error("文章信息不完整，请检查后重试");
        }

        if(articleDTO.getId() != null) {
            //TODO 执行文章更新操作
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
        articleConfig.setIsComment(articleDTO.getCloseComment()? 1 : 0);    //0可评论 , 1关闭评论

        //保存文章配置
        articleConfigMapper.insert(articleConfig);

        //设置文章内容属性
        ArticleContent articleContent = new ArticleContent();
        articleContent.setArticleId(articleId);
        articleContent.setContent(articleDTO.getContent());
        articleContentMapper.insert(articleContent);

        return Result.success("文章发布成功!");
    }

    private void updateArticle(ArticleDTO articleDTO) {

        //获取文章
        Article article = getById(articleDTO.getId());

        //更新文章
        article.setTitle(articleDTO.getTitle());
        article.setUpdateTime(LocalDateTime.now());
        article.setImages(articleDTO.getImages());
        article.setChannelId(article.getChannelId());
        //设置文章状态    (待审核状态)
        article.setStatus(ArticleStatusConstant.AWAITING_REVIEW);
        updateById(article);

        //TODO 审核 异步审核 ,计划发送消息到消息队列进行审核


        //更新文章配置
        ArticleConfig articleConfig = articleConfigMapper.selectOne(Wrappers.<ArticleConfig>lambdaQuery().eq(ArticleConfig::getArticleId, articleDTO.getId()));

        articleConfig.setIsComment(articleDTO.getCloseComment() ? 1 : 0);

        articleConfigMapper.updateById(articleConfig);

        //更新文章内容
        ArticleContent articleContent = articleContentMapper.selectOne(Wrappers.<ArticleContent>lambdaQuery().eq(ArticleContent::getArticleId, articleDTO.getId()));

        articleContent.setContent(articleDTO.getContent());
        articleContentMapper.updateById(articleContent);


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


}
