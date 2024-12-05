package icu.burtry.apis.channel;

import icu.burtry.writespacemodel.entity.Channel;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@EnableFeignClients("writespace-user")
public interface IChannelClient {

    @GetMapping("/api/v1/channel/{id}")
    Channel getChannelById(@PathVariable Long id);
}
