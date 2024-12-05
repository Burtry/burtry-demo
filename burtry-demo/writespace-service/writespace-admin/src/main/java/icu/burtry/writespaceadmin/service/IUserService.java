package icu.burtry.writespaceadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.vo.UserListVO;
import icu.burtry.writespaceutils.result.Result;

public interface IUserService extends IService<User> {
    /**
     * 获取用户列表
     * @param pageQueryDTO
     * @return
     */
    PageDTO<UserListVO> getList(PageQueryDTO pageQueryDTO);

    /**
     * 用户状态更新
     * @param id
     * @return
     */
    Result updateStatus(Long id);

    /**
     * 重置密码
     * @param id
     * @return
     */
    Result rePassword(Long id);
}
