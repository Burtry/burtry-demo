package icu.burtry.writespaceadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.ChannelDTO;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.ChannelPageQueryDTO;
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
    PageDTO<Channel> channelList(ChannelPageQueryDTO channelPageQueryDTO);

    /**
     * 启用/禁用频道
     * @param id
     * @return
     */
    Result enableChannel(Long id);

    /**
     * 删除频道
     * @param id
     * @return
     */
    Result deleteChannel(Long id);

    /**
     * 修改频道信息
     * @param channelDTO
     * @return
     */
    Result updateChannel(ChannelDTO channelDTO);
}
