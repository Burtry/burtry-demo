package icu.burtry.writespaceadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import icu.burtry.writespacemodel.dto.AdminLoginDTO;
import icu.burtry.writespacemodel.dto.AdminRegisterDTO;
import icu.burtry.writespacemodel.entity.Admin;
import icu.burtry.writespacemodel.vo.AdminVO;
import icu.burtry.writespaceutils.result.Result;

public interface ILoginService extends IService<Admin> {
    /**
     * 用户注册
     */
    Result register(AdminRegisterDTO adminRegisterDTO);

    /**
     * 用户登录
     */
    Result login(AdminLoginDTO adminLoginDTO);

}
