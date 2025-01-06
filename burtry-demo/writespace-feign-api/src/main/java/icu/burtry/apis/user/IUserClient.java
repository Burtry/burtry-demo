package icu.burtry.apis.user;

import icu.burtry.writespacemodel.entity.Channel;
import icu.burtry.writespacemodel.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("writespace-user")
public interface IUserClient {

    @GetMapping("/api/v1/user/{id}")
    User findUserById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/channel/{id}")
    Channel getChannelById(@PathVariable Long id);

}
