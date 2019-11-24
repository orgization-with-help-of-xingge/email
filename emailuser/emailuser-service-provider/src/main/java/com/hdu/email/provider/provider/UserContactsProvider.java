package com.hdu.email.provider.provider;

import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;
import com.hdu.emailuser.api.user.UserContactsApi;
import com.hdu.emailuser.biz.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userContactsProvider")
@Slf4j
public class UserContactsProvider implements UserContactsApi {

    @Autowired
    private UserGroupService userGroupService;

    @Override
    public PageView<UserGroup> queryAllGroup(UserGroupParam param) {
        PageView<UserGroup> pageView = new PageView<>();
        try {
            pageView = userGroupService.queryAll(param);

        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }

    @Override
    public BaseReturnResult updGroup(UserGroupParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = userGroupService.updGroup(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult insGroup(UserGroupParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try {
            result = userGroupService.insGroup(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public BaseReturnResult delGroup(UserGroupParam param) {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        try{
            result =   userGroupService.delGroup(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }
}
