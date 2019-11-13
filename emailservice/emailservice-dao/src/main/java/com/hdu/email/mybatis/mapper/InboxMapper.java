package com.hdu.email.mybatis.mapper;

import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InboxMapper {
    List<Inbox> selByRecipNonRead(InboxParam param);
    Integer countByRecipNonRead(InboxParam param);
    List<Inbox> selByRecip(InboxParam param);
    List<Inbox> selBySender(String sender);
    Integer countByRecip(InboxParam param);
    List<Inbox> queryAll(InboxParam param);
}
