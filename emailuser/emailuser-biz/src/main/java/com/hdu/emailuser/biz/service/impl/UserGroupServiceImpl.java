package com.hdu.emailuser.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;
import com.hdu.email.mybatis.mapper.mapper.UserGroupMapper;
import com.hdu.emailuser.biz.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public PageView<UserGroup> queryAll(UserGroupParam param) {

        PageView<UserGroup> pageView = new PageView<>();
            PageHelper.startPage(param.getPage(), param.getRows());
            List<UserGroup> userGroups = userGroupMapper.selAllGroup(param);
            pageView.setRows(userGroups);
            pageView.setTotal(userGroupMapper.countAllGroup(param));
            pageView.setWhenSuccess();

        return pageView;
    }
}
