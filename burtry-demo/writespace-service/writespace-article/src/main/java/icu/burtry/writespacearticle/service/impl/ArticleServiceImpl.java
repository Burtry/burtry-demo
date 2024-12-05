package icu.burtry.writespacearticle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespacearticle.mapper.ArticleConfigMapper;
import icu.burtry.writespacearticle.mapper.ArticleContentMapper;
import icu.burtry.writespacearticle.mapper.ArticleMapper;
import icu.burtry.writespacearticle.service.IArticleService;
import icu.burtry.writespacemodel.dto.ArticleDTO;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Result saveArticle(ArticleDTO articleDTO) {
        if (BeanUtil.isEmpty(articleDTO)) {
            return Result.error("文章信息不完整，请检查后重试");
        }

        Article article = new Article();

        //TODO 待完善
        return null;
    }
}
