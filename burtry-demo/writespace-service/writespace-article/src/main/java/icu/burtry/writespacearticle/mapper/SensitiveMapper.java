package icu.burtry.writespacearticle.mapper;

import icu.burtry.writespacemodel.entity.Sensitization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SensitiveMapper {

    @Select("select * from writespace.sensitization")
    List<Sensitization> getList();
}
