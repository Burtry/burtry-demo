package icu.burtry.writespaceuser.feign;

import icu.burtry.apis.user.IUserClient;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespaceuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserClient implements IUserClient {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @Override
    public User findUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }
}
