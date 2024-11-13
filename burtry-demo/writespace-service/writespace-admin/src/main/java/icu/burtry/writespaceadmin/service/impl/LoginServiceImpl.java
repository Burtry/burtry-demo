package icu.burtry.writespaceadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.LoginMapper;
import icu.burtry.writespaceadmin.service.ILoginService;
import icu.burtry.writespacemodel.dto.AdminLoginDTO;
import icu.burtry.writespacemodel.dto.AdminRegisterDTO;
import icu.burtry.writespacemodel.entity.Admin;
import icu.burtry.writespacemodel.vo.AdminVO;
import icu.burtry.writespaceutils.constant.SecretConstant;
import icu.burtry.writespaceutils.constant.StatusConstant;
import icu.burtry.writespaceutils.result.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Admin> implements ILoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Result register(AdminRegisterDTO adminRegisterDTO) {

        if (BeanUtil.isEmpty(adminRegisterDTO)) {
            return Result.error("注册信息为空");
        }

        //生成随机8为字符作为盐
        String salt = RandomUtil.randomString(8);

        String md5Password = MD5.create().digestHex(adminRegisterDTO.getPassword() + salt);

        Admin admin = new Admin();
        BeanUtils.copyProperties(adminRegisterDTO,admin);
        admin.setPassword(md5Password);
        admin.setCreateTime(LocalDateTime.now());
        admin.setSalt(salt);
        admin.setStatus(StatusConstant.NORMAL);
        admin.setLoginTime(LocalDateTime.now());

        //TODO 设置用户头像url
        admin.setImage("user-url.png");

        save(admin);

        return Result.success("注册成功");

    }


    @Override
    public Result login(AdminLoginDTO adminLoginDTO) {
        if (StringUtils.isBlank(adminLoginDTO.getUsername()) || StringUtils.isBlank(adminLoginDTO.getPassword())) {
            return Result.error("用户名或密码为空");
        }

        //根据用户名查询用户
        Admin admin = loginMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getName, adminLoginDTO.getUsername()));

        if (admin == null) {
            return Result.error("用户不存在");
        }

        //判断该用户是否锁定
        if (admin.getStatus().equals(StatusConstant.LOCK)) {
            return Result.error("该用户已锁定，请联系系统管理员");
        }

        String password = MD5.create().digestHex(adminLoginDTO.getPassword() + admin.getSalt());

        if (!password.equals(admin.getPassword())) {
            return Result.error("密码错误");
        }

        //生成token
        Map<String, Object> payload = new HashMap<>();
        payload.put("adminId",admin.getId());
        payload.put("adminName",admin.getName());
        String token = JWTUtil.createToken(payload, SecretConstant.SECRET.getBytes());

        AdminVO adminVO = new AdminVO();
        BeanUtils.copyProperties(admin,adminVO);
        adminVO.setToken(token);

        return Result.success(adminVO,"登录成功!");


    }
}
