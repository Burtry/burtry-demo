package icu.burtry.writespaceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.UpdateUserInfoDTO;
import icu.burtry.writespacemodel.entity.User;

public interface UserService extends IService<User> {
    /**
     * 更新用户信息
     * @param userInfoDTO 用户信息DTO
     */
    void updateInfo(UpdateUserInfoDTO userInfoDTO);
}
