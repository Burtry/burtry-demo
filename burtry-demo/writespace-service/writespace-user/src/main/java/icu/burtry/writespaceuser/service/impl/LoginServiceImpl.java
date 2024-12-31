package icu.burtry.writespaceuser.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespacemodel.dto.UserLoginDTO;
import icu.burtry.writespacemodel.dto.UserRegisterDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.vo.UserVO;
import icu.burtry.writespaceuser.mapper.LoginMapper;
import icu.burtry.writespaceuser.service.LoginService;
import icu.burtry.writespaceutils.constant.StatusConstant;
import icu.burtry.writespaceutils.constant.VisitorConstant;
import icu.burtry.writespaceutils.result.Result;
import icu.burtry.writespaceutils.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
public class LoginServiceImpl extends ServiceImpl<LoginMapper, User> implements LoginService {

    @Autowired
    private LoginMapper loginMapper;
    
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result register(UserRegisterDTO userRegisterDTO) {
        if (BeanUtil.isEmpty(userRegisterDTO)) {
            return Result.error("注册信息为空");
        }
        
        //判断验证码是否正确
        String captcha = redisTemplate.opsForValue().get("user_" + userRegisterDTO.getName() + "_code");
        if (captcha == null) {
            return Result.error("请重新输入验证码");
        }
        if (!captcha.equals(userRegisterDTO.getCaptcha())) {
            return Result.error("验证码错误");
        }


        //判断密码是否相同
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return Result.error("密码不相同");
        }

        //判断用户是否存在
        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getName, userRegisterDTO.getName()));

        if (user != null) {
            return Result.error("用户已存在");
        }

        //    生成随机八位字符串作为盐
        String slat = RandomUtil.randomString(8);
        String md5Password = MD5.create().digestHex(userRegisterDTO.getPassword() + slat);

        User newuser = new User();
        BeanUtils.copyProperties(userRegisterDTO,newuser);

        newuser.setPassword(md5Password);
        newuser.setCreateTime(LocalDateTime.now());
        newuser.setSalt(slat);
        newuser.setStatus(StatusConstant.NORMAL);

        //TODO 设置用户url
        newuser.setImage("user-url.png");

        save(newuser);

        return Result.success("注册成功");
    }

    @Override
    public Result<UserVO> login(UserLoginDTO userLoginDTO) {
        if (userLoginDTO.getUsername().equals(VisitorConstant.VISITOR)) {
            //生成token
            String token = JwtUtil.getToken(0L, "user");
            UserVO userVO = new UserVO();
            userVO.setId(0L);
            userVO.setToken(token);
            return Result.success(userVO,"游客进入");
        }
        if(userLoginDTO.getPassword() == null) {
            return Result.error("登录信息不完整");
        }


        //判断验证码是否正确
        String captcha = redisTemplate.opsForValue().get("user_" + userLoginDTO.getUsername() + "_code");
        if (captcha == null) {
            return Result.error("请重新输入验证码");
        }
        if (!captcha.equals(userLoginDTO.getCaptcha())) {
            return Result.error("验证码错误");
        }

        User user = getOne(Wrappers.<User>lambdaQuery().eq(User::getName, userLoginDTO.getUsername()));
        if (user == null) {
            return Result.error("用户不存在");
        }

    //    判断密码是否正确
        String salt = user.getSalt();
        String password = MD5.create().digestHex(userLoginDTO.getPassword() + salt);

        if (!password.equals(user.getPassword())) {
            return Result.error("密码错误");
        }

        //判断用户是否锁定
        if(user.getStatus() == 1) {
            return Result.error("用户已锁定!请联系管理员!");
        }

        //生成token
        String token = JwtUtil.getToken(user.getId(), "user");

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        userVO.setToken(token);

        return Result.success(userVO,"登录成功!");

    }
}







