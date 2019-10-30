package com.hdu.email.mybatis.mapper;

import com.hdu.email.dto.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
    List<Users> selAll();

    Users selById(@Param(value = "userid") String userid);

}
