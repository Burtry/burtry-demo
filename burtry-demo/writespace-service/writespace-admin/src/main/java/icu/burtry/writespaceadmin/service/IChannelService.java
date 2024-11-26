package icu.burtry.writespaceadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.ChannelDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespaceutils.result.Result;

public interface IChannelService extends IService<Channel> {

    /**
     * 添加频道
     * @param channelDTO
     */
    Result addChannel(ChannelDTO channelDTO);

    /**
     * 获取频道列表
     * @return
     */
    PageDTO<Channel> channelList(PageQueryDTO pageQueryDTO);
}
