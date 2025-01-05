package icu.burtry.writespaceuserbehavior;

import icu.burtry.writespacemodel.dto.LikeBehaviorDTO;
import icu.burtry.writespaceuserbehavior.service.IUserBehaviorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WritespaceUserBehaviorApplicationTests {

    @Autowired
    private IUserBehaviorService behaviorService;

    @Test
    void contextLoads() {
        LikeBehaviorDTO likeBehaviorDTO = new LikeBehaviorDTO();

    }

}
