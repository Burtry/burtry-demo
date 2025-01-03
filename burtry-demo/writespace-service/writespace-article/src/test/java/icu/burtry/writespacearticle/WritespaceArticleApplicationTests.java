package icu.burtry.writespacearticle;
import icu.burtry.writespacearticle.service.IArticleService;
import icu.burtry.writespacemodel.dto.ArticleLoadDTO;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespaceutils.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WritespaceArticleApplicationTests {

	@Autowired
	private IArticleService service;
	@Test
	void contextLoads() {

		ArticleLoadDTO articleLoadDTO = new ArticleLoadDTO();
		articleLoadDTO.setChannelId(3L);
		//articleLoadDTO.setPageSize(11);
		articleLoadDTO.setPageNum(2);
		Result<List<ArticleVO>> load = service.load(articleLoadDTO);

		System.out.println(load);
	}

}
