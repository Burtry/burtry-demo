package icu.burtry.writespaceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespacemodel.dto.UpdateUserInfoDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.vo.UserInfoVO;
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

    @Override
    public Result updateUserInfo(UpdateUserInfoDTO userInfoDTO) {
        if (BeanUtil.isEmpty(userInfoDTO)) {
            return Result.error("信息不完整!");
        }
        User user = getById(userInfoDTO.getId());
        if(user == null) {
            return Result.error("用户不存在!");
        }

        user.setPhone(userInfoDTO.getPhone());
        user.setImage(userInfoDTO.getUrl());//设置头像
        user.setSex(userInfoDTO.getSex());
        user.setNickName(userInfoDTO.getNickName());
        user.setEmail(userInfoDTO.getEmail());
        user.setAddress(userInfoDTO.getAddress());

        updateById(user);

        return Result.success("更新成功!" );
    }
}
