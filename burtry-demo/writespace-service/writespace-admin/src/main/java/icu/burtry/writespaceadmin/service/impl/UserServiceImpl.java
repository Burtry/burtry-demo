package icu.burtry.writespaceadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.burtry.writespaceadmin.mapper.UserMapper;
import icu.burtry.writespaceadmin.service.IUserService;
import icu.burtry.writespacemodel.dto.PageDTO;
import icu.burtry.writespacemodel.dto.PageQueryDTO;
import icu.burtry.writespacemodel.entity.User;
import icu.burtry.writespacemodel.vo.UserListVO;
import icu.burtry.writespaceutils.constant.PasswordConstant;
import icu.burtry.writespaceutils.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public PageDTO<UserListVO> getList(PageQueryDTO pageQueryDTO) {

        if (BeanUtil.isEmpty(pageQueryDTO, pageQueryDTO.getKeyword(), pageQueryDTO.getSortBy())) {
            return new PageDTO<>(-1L,1L, Collections.emptyList());
        }

        Page<User> page = new Page<>(pageQueryDTO.getPageNum(), pageQueryDTO.getPageSize());

        //构建查询条件
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        if (pageQueryDTO.getSortBy() == null) {
            //默认根据创建时间降序
            userQueryWrapper.orderByDesc("create_time");
        }

        //关键字条件
        if (pageQueryDTO.getKeyword() != null && !pageQueryDTO.getKeyword().isEmpty()) {
            //判断是name还是id
            if (pageQueryDTO.getKeyword().matches("\\d+")) {    //验证是否全为数字，全为数字则为id,转成Long
                long id = Long.parseLong(pageQueryDTO.getKeyword());
                userQueryWrapper.eq("id",id);
            } else {
                userQueryWrapper.like("name",pageQueryDTO.getKeyword());
            }
        }

        page = userMapper.selectPage(page,userQueryWrapper);

        List<User> userList = page.getRecords();


        if (userList.isEmpty()) {
            return new PageDTO<>(page.getTotal(),page.getPages(),Collections.emptyList());
        }  else {

            //转换成VO
            List<UserListVO> userListVOS = new ArrayList<>();
            for (User user : userList) {
                UserListVO userListVO = new UserListVO();
                BeanUtils.copyProperties(user,userListVO);
                userListVOS.add(userListVO);
            }
            return new PageDTO<>(page.getTotal(),page.getPages(),userListVOS);
        }
    }

    @Override
    public Result updateStatus(Long id) {
        if (id == null) {
            return Result.error("参数不合法");
        }
        User user = getById(id);
        if (user == null) {
            return Result.error("用户不存在");
        }
        Integer status = user.getStatus();
        if(status == 1) {   //0正常 1锁定
            user.setStatus(0);
        } else  {
            user.setStatus(1);
        }
        updateById(user);
        return Result.success("修改成功");
    }

    @Override
    public Result rePassword(Long id) {
        if (id == null) {
            return Result.error("参数错误");
        }
        User user = getById(id);
        if(user == null) {
            return Result.error("用户不存在");
        }

        String salt = user.getSalt();
        String password = MD5.create().digestHex(PasswordConstant.DEFAULT_PASSWORD + salt);

        user.setPassword(password);
        updateById(user);
        return Result.success("更新成功");
    }
}
