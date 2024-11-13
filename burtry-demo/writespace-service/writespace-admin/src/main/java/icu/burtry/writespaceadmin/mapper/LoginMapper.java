package icu.burtry.writespaceadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.burtry.writespacemodel.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper extends BaseMapper<Admin> {
}
