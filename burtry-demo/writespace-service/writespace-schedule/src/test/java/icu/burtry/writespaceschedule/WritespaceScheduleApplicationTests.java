package icu.burtry.writespaceschedule;

import icu.burtry.writespacemodel.entity.article.Article;
import icu.burtry.writespaceschedule.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WritespaceScheduleApplicationTests {

	@Autowired
	private ArticleMapper articleMapper;

	@Test
	void contextLoads() {
		List<Article> articleList =
				articleMapper.selectList();

		System.out.println(articleList);
	}

}
