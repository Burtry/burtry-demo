package icu.burtry.writespaceuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespacemodel.dto.UpdateUserInfoDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespaceuser.mapper.UserMapper;
import icu.burtry.writespaceuser.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public void updateInfo(UpdateUserInfoDTO userInfoDTO) {

    }
}
