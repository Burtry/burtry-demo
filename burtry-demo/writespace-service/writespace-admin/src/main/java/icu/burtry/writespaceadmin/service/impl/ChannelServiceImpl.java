package icu.burtry.writespaceadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.ChannelMapper;
import icu.burtry.writespaceadmin.service.IChannelService;
import icu.burtry.writespacemodel.dto.ChannelDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.ChannelPageQueryDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public Result addChannel(ChannelDTO channelDTO) {
        if (channelDTO.getName().isEmpty() || channelDTO.getDescription().isEmpty()) {
            return Result.error("参数不能为空!");
        }

        Channel ch = getOne(Wrappers.<Channel>lambdaQuery().eq(Channel::getName, channelDTO.getName()));
        if (ch != null) {
            return Result.error("已存在该频道!");
        }

        Channel channel = new Channel();

        BeanUtils.copyProperties(channelDTO,channel);
        channel.setStatus(1);   //默认不可用状态
        channel.setCreateTime(LocalDateTime.now());
        save(channel);
        return Result.success("频道添加成功!");
    }

    @Override
    public PageDTO<Channel> channelList(ChannelPageQueryDTO channelPageQueryDTO) {

        if (channelPageQueryDTO.getPageNum() == null || channelPageQueryDTO.getPageSize() == null || BeanUtil.isEmpty(channelPageQueryDTO)) {
            return new PageDTO<>(0L,1L,Collections.emptyList());
        }

        Page<Channel> page = new Page<>(channelPageQueryDTO.getPageNum(), channelPageQueryDTO.getPageSize());

        QueryWrapper<Channel> channelQueryWrapper = new QueryWrapper<>();
        if (channelPageQueryDTO.getSortBy() == null || channelPageQueryDTO.getSortBy().isEmpty()) {
            //默认创建时间降序排列
            channelQueryWrapper.orderByDesc("create_time");
        } else  {
            channelQueryWrapper.orderByDesc(channelPageQueryDTO.getSortBy());
        }

        //判断keyword
        if (!channelPageQueryDTO.getKeyword().isEmpty()) {
            channelQueryWrapper.like("name", channelPageQueryDTO.getKeyword());
        }
        //判断status
        if (channelPageQueryDTO.getStatus() != 2) {    //不为全部频道添加查询条件
            channelQueryWrapper.like("status", channelPageQueryDTO.getStatus());
        }

        Page<Channel> channelPage = channelMapper.selectPage(page, channelQueryWrapper);

        List<Channel> channelList = channelPage.getRecords();

        if (channelList.isEmpty()) {
            return new PageDTO<>(channelPage.getTotal(),channelPage.getPages(), Collections.emptyList());
        } else {
            return new PageDTO<>(channelPage.getTotal(),channelPage.getPages(),channelList);
        }

    }

    @Override
    public Result enableChannel(Long id) {

        Channel channel = getById(id);
        if(channel == null) {
            return Result.error("频道不存在");
        }

        channel.setStatus(channel.getStatus() == 0 ? 1 : 0);    //修改成相反状态
        updateById(channel);
        return Result.success("修改成功");
    }

    @Override
    public Result deleteChannel(Long id) {

        Channel channel = getById(id);
        if(channel == null) {
            return Result.error("频道不存在");
        }
        removeById(id);

        return Result.success("删除成功");
    }

    @Override
    public Result updateChannel(ChannelDTO channelDTO) {
        if (BeanUtil.isEmpty(channelDTO)) {
            return Result.error("修改信息不能为空");
        }
        if(channelDTO.getId() == null) {
            return Result.error("频道不存在");
        }
        Channel channel = getById(channelDTO.getId());
        if(channel == null) {
            return Result.error("频道不存在");
        }
        channel.setName(channelDTO.getName());
        channel.setDescription(channelDTO.getDescription());
        channel.setStatus(1); //修改信息后改为禁用状态
        updateById(channel);
        return Result.success("修改成功");
    }


}
