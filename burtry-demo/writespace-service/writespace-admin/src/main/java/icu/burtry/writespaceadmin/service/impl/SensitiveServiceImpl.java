package icu.burtry.writespaceadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.SensitiveMapper;
import icu.burtry.writespaceadmin.service.ISensitiveService;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.dto.SensitiveDTO;
import icu.burtry.writespacemodel.entity.Sensitization;
import icu.burtry.writespaceutils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SensitiveServiceImpl extends ServiceImpl<SensitiveMapper, Sensitization> implements ISensitiveService {

    @Autowired
    private SensitiveMapper sensitiveMapper;


    @Override
    public Result addSensitive(String sensitive) {

        if (Objects.equals(sensitive, "") || sensitive.isEmpty()) {
            return Result.error("敏感词不能为空");
        }

        Sensitization sensitization1 = getOne(Wrappers.<Sensitization>lambdaQuery().eq(Sensitization::getSensitiveName, sensitive));

        if (sensitization1 != null) {
            return Result.error("敏感词已存在");
        }

        Sensitization sensitization = new Sensitization();
        sensitization.setSensitiveName(sensitive);
        sensitization.setCreateTime(LocalDateTime.now());
        sensitization.setUpdateTime(LocalDateTime.now());
        save(sensitization);

        return Result.success("添加成功!");
    }

    @Override
    public Result deleteSensitive(Long id) {
        removeById(id);
        return Result.success("删除成功");
    }


    @Override
    public Result updateSensitive(SensitiveDTO sensitiveDTO) {
        if (BeanUtil.isEmpty(sensitiveDTO)) {
            return Result.error("参数错误");
        }
        Sensitization sensitization = getById(sensitiveDTO.getId());

        if (sensitization == null) {
            return Result.error("敏感词不存在");
        }

        sensitization.setSensitiveName(sensitiveDTO.getSensitiveName());
        sensitization.setUpdateTime(LocalDateTime.now());
        updateById(sensitization);

        return Result.success("更新成功");

    }

    @Override
    public PageDTO<Sensitization> getList(PageQueryDTO pageQueryDTO) {
        if (BeanUtil.isEmpty(pageQueryDTO)) {
            //参数异常
            return new PageDTO<>(-1L,1L,Collections.emptyList());
        }
        Page<Sensitization> page = new Page<>(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());

        QueryWrapper<Sensitization> sensitiveQueryWrapper = new QueryWrapper<>();

        if (pageQueryDTO.getSortBy() == null || pageQueryDTO.getSortBy().isEmpty()) {
            //默认以更新时间排序
            sensitiveQueryWrapper.orderByDesc("update_time");
        } else {
            sensitiveQueryWrapper.orderByDesc(pageQueryDTO.getSortBy());
        }

        if (!pageQueryDTO.getKeyword().isEmpty()) {
            sensitiveQueryWrapper.like("sensitive_name",pageQueryDTO.getKeyword());
        }

        //查询
        page = sensitiveMapper.selectPage(page, sensitiveQueryWrapper);

        List<Sensitization> sensitizationList = page.getRecords();

        if (sensitizationList.isEmpty()) {
            return new PageDTO<>(page.getTotal(),page.getPages(),Collections.emptyList());
        } else {
            return new PageDTO<>(page.getTotal(),page.getPages(), sensitizationList);
        }

    }
}
