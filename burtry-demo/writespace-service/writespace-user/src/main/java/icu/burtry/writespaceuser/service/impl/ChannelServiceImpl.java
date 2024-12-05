package icu.burtry.writespaceuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespaceuser.mapper.ChannelMapper;
import icu.burtry.writespaceuser.service.IChannelService;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public Result channelList() {
        QueryWrapper<Channel> channelQueryWrapper = new QueryWrapper<>();
        //查询启用列表
        channelQueryWrapper.eq("status",0);

        List<Channel> channels = channelMapper.selectList(channelQueryWrapper);

        return Result.success(channels,"列表获取成功");
    }
}
