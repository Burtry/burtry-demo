package icu.burtry.writespaceuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespacemodel.dto.UpdateUserInfoDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.vo.UserInfoVO;
import icu.burtry.writespacemodel.vo.UserVO;
import icu.burtry.writespaceuser.mapper.UserMapper;
import icu.burtry.writespaceuser.service.UserService;
import icu.burtry.writespaceutils.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Result getUserInfo(Long id) {
        if(id < 0) {
            return Result.error("id不合法");
        }
        User user = getById(id);
        if(user == null) {
            return Result.error("用户不存在");
        }
        UserInfoVO userVO = new UserInfoVO();
        BeanUtils.copyProperties(user,userVO);

        return Result.success(userVO,"用户信息获取成功！");
    }
}
