package com.hdu.emailuser.biz.service;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;

import java.util.List;

/**
 * 功能描述: 通讯组逻辑类
 * @Author: sixl
 * @Date: 2019/11/20 20:46
 */
public interface UserGroupService {
    PageView<UserGroup> queryAll(UserGroupParam param);
}
