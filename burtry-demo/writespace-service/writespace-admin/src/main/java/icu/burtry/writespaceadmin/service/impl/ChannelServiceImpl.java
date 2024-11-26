package icu.burtry.writespaceadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.ChannelMapper;
import icu.burtry.writespaceadmin.service.IChannelService;
import icu.burtry.writespacemodel.dto.ChannelDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
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
    public PageDTO<Channel> channelList(PageQueryDTO pageQueryDTO) {
        Page<Channel> page = new Page<>(pageQueryDTO.getPageNum(),pageQueryDTO.getPageSize());

        QueryWrapper<Channel> channelQueryWrapper = new QueryWrapper<>();
        if (pageQueryDTO.getSortBy() == null || pageQueryDTO.getSortBy().isEmpty()) {
            //默认创建时间降序排列
            channelQueryWrapper.orderByDesc("create_time");
        } else  {
            channelQueryWrapper.orderByDesc(pageQueryDTO.getSortBy());
        }

        Page<Channel> channelPage = channelMapper.selectPage(page, channelQueryWrapper);

        List<Channel> channelList = channelPage.getRecords();

        if (channelList.isEmpty()) {
            return new PageDTO<>(channelPage.getTotal(),channelPage.getPages(), Collections.emptyList());
        } else {
            return new PageDTO<>(channelPage.getTotal(),channelPage.getPages(),channelList);
        }

    }
}
