package icu.burtry.writespaceuser.feign;

import icu.burtry.apis.channel.IChannelClient;
import icu.burtry.apis.user.IUserClient;
import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespaceuser.service.IChannelService;
import icu.burtry.writespaceuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelClient implements IChannelClient {

    @Autowired
    private IChannelService channelService;

    @GetMapping("/{id}")
    @Override
    public Channel getChannelById(@PathVariable("id") Long id) {
        return channelService.getById(id);
    }
}
