package com.hdu.emailuser.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.hdu.email.common.util.transfer.BaseReturnResult;
import com.hdu.email.common.util.transfer.PageView;
import com.hdu.email.dto.EmailContacts;
import com.hdu.email.dto.EmailContactsParam;
import com.hdu.email.dto.UserGroup;
import com.hdu.email.dto.UserGroupParam;
import com.hdu.email.mybatis.mapper.mapper.ContactsMapper;
import com.hdu.email.mybatis.mapper.mapper.UserGroupMapper;
import com.hdu.emailuser.biz.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Override
    public PageView<UserGroup> queryAll(UserGroupParam param) {
        PageView<UserGroup> pageView = new PageView<>();
        PageHelper.startPage(param.getPage(), param.getRows());
        List<UserGroup> userGroups = userGroupMapper.selAllGroup(param);
        EmailContactsParam contactsParam = new EmailContactsParam();
        for (UserGroup userGroup : userGroups) {
            contactsParam.setGroupid(userGroup.getUrid());
            contactsParam.setUsername(param.getUsername());
            List<EmailContacts> emailContacts = contactsMapper.selByUserName(contactsParam);
            userGroup.setContacts(emailContacts);
        }
        pageView.setRows(userGroups);
        pageView.setTotal(userGroupMapper.countAllGroup(param));
        pageView.setWhenSuccess();
        return pageView;
    }

    @Override
    public BaseReturnResult updGroup(UserGroupParam param) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        //每次操作前应该先把这个组所有成员的组别置空，在重新插入
        int j= contactsMapper.updGroupIdNull(param.getUrid());
        int i = userGroupMapper.updGroup(param);
        int sum=0;
        EmailContactsParam contactsParam = new EmailContactsParam();
        for (String contact : param.getContacts()) {
            contactsParam.setGroupid(param.getUrid());
            contactsParam.setUrid(contact);
            sum += contactsMapper.updContacts(contactsParam);
        }
        if (sum<param.getContacts().size() || i<1 || j<1){
            throw  new Exception("操作失败");
        }
        result.setWhenSuccess();
        return result;
    }

    @Override
    public BaseReturnResult insGroup(UserGroupParam param) throws Exception {
        BaseReturnResult result = BaseReturnResult.getFailResult();
        //插入用户组一共要进行1.用户组表插入 2.更新选中的用户
        param.setUrid(UUID.randomUUID().toString());
        int i = userGroupMapper.insGroup(param);
        int sum=0;
        EmailContactsParam contactsParam = new EmailContactsParam();
        if (param.getContacts()!=null&&param.getContacts().size()!=0){
            for (String contact : param.getContacts()) {
                contactsParam.setUrid(contact);
                contactsParam.setGroupid(param.getUrid());
                sum += contactsMapper.updContacts(contactsParam);
            }
        }
        if (i<1 || sum<param.getContacts().size()){
            throw new Exception("操作失败");
        }

        result.setWhenSuccess();
        return result;
    }

    @Override
    public BaseReturnResult delGroup(UserGroupParam param) throws Exception {
        //删除逻辑：1.从分组表里删除 2.将此用户组的用户的groupid置空
        BaseReturnResult result = BaseReturnResult.getFailResult();
        int i = userGroupMapper.delGroup(param);
        int j = contactsMapper.updGroupIdNull(param.getUrid());
        if (i<1 || j<1){
            throw new Exception("操作失败");
        }
        result.setWhenSuccess();
        return result;
    }


}
