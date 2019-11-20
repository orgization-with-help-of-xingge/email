package com.hdu.email.provider.provider;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;
import com.hdu.emailuser.api.user.UserContactsApi;
import org.springframework.stereotype.Service;

@Service(value = "userContactsProvider")
public class UserContactsProvider implements UserContactsApi {


    @Override
    public PageView<UserGroup> queryAllGroup(UserGroupParam param) {
        return null;
    }
}
