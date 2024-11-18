package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {

    //用户名
    private String name;

    //密码
    private String password;

    //确认密码
    private String confirmPassword;

    //性别
    private Integer sex;

    //手机号
    private String phone;

    //昵称
    private String nickName;

    //邮箱
    private String email;

    //地址
    private String address;

    //验证码
    private String captcha;
}

