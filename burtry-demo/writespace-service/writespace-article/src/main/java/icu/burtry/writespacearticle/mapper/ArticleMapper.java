package icu.burtry.writespacearticle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.burtry.writespacemodel.entity.article.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;
import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 多表联查，查询未删除的文章列表
     * @param list
     * @return
     */
    List<Article> getNoDeleteArticle(List<Long> list,Long id,Integer status);


    /**
     * 多表联查,查询未删除文章列表，并且频道不为1 ,2 3 时，添加频道条件，根据发布时间降序排序
     * @param page
     * @param channelId
     * @return
     */
    IPage<Article> loadArticles(Page<Article> page, @PathParam("channelId") Long channelId);
}
