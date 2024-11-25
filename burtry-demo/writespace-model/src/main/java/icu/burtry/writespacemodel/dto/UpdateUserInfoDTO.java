package icu.burtry.writespacemodel.dto;

import lombok.Data;

@Data
public class UpdateUserInfoDTO {

    //用户id
    private Long id;

    //手机号
    private String phone;

    //头像url
    private String url;

    //性别
    private Integer sex;

    //昵称
    private String nickName;

    //邮箱
    private String email;

    //地址
    private String address;

}
