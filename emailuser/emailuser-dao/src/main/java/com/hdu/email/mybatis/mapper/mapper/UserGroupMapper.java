package com.hdu.email.mybatis.mapper.mapper;

import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;

import java.util.List;

/**
 * 功能描述: usergroup操作
 * @Author: sixl
 * @Date: 2019/11/20 20:20
 */
public interface UserGroupMapper {
    List<UserGroup> selAllGroup(UserGroupParam userGroupParam);

    int countAllGroup(UserGroupParam username);

    int insGroup(UserGroupParam userGroup);

    int updGroup(UserGroupParam userGroup);

    int delGroup(UserGroupParam userGroup);

}
