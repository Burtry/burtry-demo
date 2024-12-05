package icu.burtry.writespaceadmin.controller;

import icu.burtry.writespaceadmin.service.IUserService;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.vo.UserListVO;
import icu.burtry.writespaceutils.result.Result;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping()
    public Result<PageDTO<UserListVO>> getUserList(PageQueryDTO pageQueryDTO) {
        log.info("获取用户列表:{}",pageQueryDTO);
        PageDTO<UserListVO> userList = userService.getList(pageQueryDTO);
        if (userList.getTotal() == -1) {
            return Result.error("请求参数有误，请重试");
        }

        return Result.success(userList,"获取成功");
    }

    @PutMapping()
    public Result updateStatus(@NonNull Long id) {
        log.info("用户状态更新:{}",id);
        return userService.updateStatus(id);
    }

    @PutMapping("/rePassword")
    public Result reWritePassword(@NonNull Long id) {
        log.info("重置密码:{}",id);
        return userService.rePassword(id);
    }

}
