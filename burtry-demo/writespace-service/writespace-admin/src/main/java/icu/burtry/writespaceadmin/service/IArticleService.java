package icu.burtry.writespaceadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.AdminArticleSearchDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespaceutils.result.Result;

import java.util.List;

public interface IArticleService extends IService<Article> {
    /**
     * 获取频道列表
     * @return
     */
    Result<List<Channel>> getChannelList();

    /**
     * 获取文章列表
     * @param adminArticleSearchDTO
     * @return
     */
    Result<PageDTO<Article>> getArticleList(AdminArticleSearchDTO adminArticleSearchDTO);
}
