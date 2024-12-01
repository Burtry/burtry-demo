package icu.burtry.writespaceadmin.controller;

import icu.burtry.writespaceadmin.service.ISensitiveService;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.dto.SensitiveDTO;
import icu.burtry.writespacemodel.entity.Sensitization;
import icu.burtry.writespaceutils.result.Result;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sensitive")
@Slf4j
public class SensitiveController {

    @Autowired
    private ISensitiveService sensitiveService;

    @PostMapping()
    public Result addSensitive(String sensitiveName) {
        log.info("添加敏感词");
        return sensitiveService.addSensitive(sensitiveName);
    }

    @DeleteMapping()
    public Result deleteSensitive(@NonNull Long id) {
        log.info("删除敏感词");
        return sensitiveService.deleteSensitive(id);
    }

    @PutMapping()
    public Result updateSensitive(@RequestBody SensitiveDTO sensitiveDTO) {
        log.info("修改敏感词");
        return sensitiveService.updateSensitive(sensitiveDTO);
    }

    @GetMapping()
    public Result<PageDTO<Sensitization>> getList(PageQueryDTO pageQueryDTO) {
        log.info("分页获取敏感词");
        PageDTO<Sensitization> sensitiveList = sensitiveService.getList(pageQueryDTO);
        if (sensitiveList.getTotal() == -1) {
            return Result.error("参数异常,重试");
        }
        return Result.success(sensitiveList,"获取成功");
    }

}
