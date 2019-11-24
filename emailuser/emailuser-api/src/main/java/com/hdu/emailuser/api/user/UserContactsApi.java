package com.hdu.emailuser.api.user;


import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;

public interface UserContactsApi {
    PageView<UserGroup> queryAllGroup(UserGroupParam param);

    BaseReturnResult updGroup(UserGroupParam param);

    BaseReturnResult insGroup(UserGroupParam param);

    BaseReturnResult delGroup(UserGroupParam param);
}
