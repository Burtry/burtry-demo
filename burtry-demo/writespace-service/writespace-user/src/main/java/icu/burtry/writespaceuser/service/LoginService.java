package icu.burtry.writespaceuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.UserRegisterDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespaceutils.result.Result;

public interface LoginService extends IService<User> {
    /**
     * 用户注册
     * @param userRegisterDTO 注册dto
     * @return
     */
    Result register(UserRegisterDTO userRegisterDTO);
}
