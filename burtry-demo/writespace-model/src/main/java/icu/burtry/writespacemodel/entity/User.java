package icu.burtry.writespacemodel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    //id
    @TableId(type = IdType.AUTO)
    private Long id;

    //用户名
    private String name;

    //密码
    private String password;

    //盐
    private String salt;

    //性别
    private Integer sex;

    //手机号
    private String phone;

    //头像url
    private String image;

    //昵称
    private String nickName;

    //邮箱
    private String email;
    //状态
    private Integer status;

    //创建时间
    private LocalDateTime createTime;

    //地址
    private String address;


}
