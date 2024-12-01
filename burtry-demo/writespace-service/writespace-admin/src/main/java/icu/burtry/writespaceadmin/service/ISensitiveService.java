package icu.burtry.writespaceadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.dto.SensitiveDTO;
import icu.burtry.writespacemodel.entity.Sensitization;
import icu.burtry.writespaceutils.result.Result;

public interface ISensitiveService extends IService<Sensitization> {

    /**
     * 添加敏感词
     * @param sensitive
     * @return
     */
    Result addSensitive(String sensitive);

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    Result deleteSensitive(Long id);

    /**
     * 修改敏感词
     * @param sensitiveDTO
     * @return
     */
    Result updateSensitive(SensitiveDTO sensitiveDTO);

    /**
     * 分页获取敏感词列表
     * @return
     */
    PageDTO<Sensitization> getList(PageQueryDTO pageQueryDTO);

}
