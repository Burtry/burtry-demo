package icu.burtry.writespaceschedule;

import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespaceschedule.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WritespaceScheduleApplicationTests {

	@Autowired
	private ArticleMapper articleMapper;

	@Test
	void contextLoads() {
		List<Long> list = new ArrayList<>();
		list.add(1879772675312848896L);
		list.add(1866691004992720896L);
		list.add(1866691470401081344L);
		//List<Article> articleList = articleMapper.selectListByIds(list);

		//System.out.println(articleList);

		List<Article> articleList = articleMapper.selectArticlesWithinThreeDays(list);
		System.out.println(articleList);
	}

}
