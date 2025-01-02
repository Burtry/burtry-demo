package icu.burtry.writespaceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.UpdateUserInfoDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespaceutils.result.Result;

public interface UserService extends IService<User> {

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    Result getUserInfo(Long id);

    /**
     * 更新用户信息
     * @param userInfoDTO
     * @return
     */
    Result updateUserInfo(UpdateUserInfoDTO userInfoDTO);
}
