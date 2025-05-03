package com.qfleaf.yunbi.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfleaf.yunbi.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("select * from users ${ew.customSqlSegment}")
    User findByUsernameOrPhoneOrEmail(@Param("ew") Wrapper<User> wrapper);
}
