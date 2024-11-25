package icu.burtry.writespaceuser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.burtry.writespacemodel.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
