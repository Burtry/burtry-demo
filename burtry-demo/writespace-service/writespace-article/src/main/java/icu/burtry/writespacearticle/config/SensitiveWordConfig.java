package icu.burtry.writespacearticle.config;


import cn.hutool.dfa.WordTree;
import icu.burtry.writespacearticle.mapper.SensitiveMapper;
import icu.burtry.writespacemodel.entity.Sensitization;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class SensitiveWordConfig {

    private SensitiveMapper sensitiveMapper;
    public SensitiveWordConfig(SensitiveMapper sensitiveWordMapper) {
        this.sensitiveMapper = sensitiveWordMapper;
    }

    /**
     * 加载敏感词列表并初始化 WordTree
     */
    @Bean
    public WordTree sensitiveWordTree() {
        WordTree wordTree = new WordTree();
        try {
            // 从数据库加载敏感词
            List<Sensitization> list = sensitiveMapper.getList();

            log.info("从数据库加载敏感词中...: {}", list);
            for (Sensitization sensitization : list) {
                // 将敏感词添加到 WordTree
                wordTree.addWords(sensitization.getSensitiveName());
            }
        } catch (Exception e) {
            log.error("加载敏感词失败", e);
        }
        return wordTree;
    }

}
