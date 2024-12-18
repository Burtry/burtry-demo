package icu.burtry.writespacemodel.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVO {

    //id
    private Long id;

    //手机号
    private String phone;

    //头像url
    private String image;

    //昵称
    private String nickName;

    //性别
    private Integer sex;

    //邮箱
    private String email;

    //地址
    private String address;

    //状态
    private Integer status;

    //创建时间
    private LocalDateTime createTime;

}
