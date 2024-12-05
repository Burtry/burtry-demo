package icu.burtry.writespaceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespaceutils.result.Result;

public interface IChannelService extends IService<Channel> {

    /**
     * 获取频道列表
     * @return
     */
    Result channelList();
}
