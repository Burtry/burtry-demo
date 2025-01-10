package icu.burtry.writespacearticle;
import cn.hutool.dfa.WordTree;
import icu.burtry.writespacearticle.config.RabbitMQConfig;
import icu.burtry.writespacearticle.mapper.SensitiveMapper;
import icu.burtry.writespacearticle.service.IArticleService;
import icu.burtry.writespacemodel.dto.ArticleLoadDTO;
import icu.burtry.writespacemodel.dto.ArticleVerifyMessageDTO;
import icu.burtry.writespacemodel.entity.Sensitization;
import icu.burtry.writespacemodel.vo.ArticleVO;
import icu.burtry.writespaceutils.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class WritespaceArticleApplicationTests {

	@Autowired
	private IArticleService service;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private SensitiveMapper sensitiveMapper;

	@Autowired
	private  WordTree wordTree;



	@Test
	void contextLoads() {

		ArticleLoadDTO articleLoadDTO = new ArticleLoadDTO();
		articleLoadDTO.setChannelId(3L);
		//articleLoadDTO.setPageSize(11);
		articleLoadDTO.setPageNum(2);
		Result<List<ArticleVO>> load = service.load(articleLoadDTO);

		System.out.println(load);
	}

	@Test
	void test() {
		ArticleVerifyMessageDTO articleVerifyMessageDTO = new ArticleVerifyMessageDTO();
		articleVerifyMessageDTO.setArticleId(1L);
		articleVerifyMessageDTO.setTitle("123");
		articleVerifyMessageDTO.setContent("内容");
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY,articleVerifyMessageDTO);
	}


	@Test
	void test2() {
		List<String> list = wordTree.matchAll("冰毒awdawdawdaw冰毒2 ...毒药。。测试啊");

		System.out.println(list);
	}
}
