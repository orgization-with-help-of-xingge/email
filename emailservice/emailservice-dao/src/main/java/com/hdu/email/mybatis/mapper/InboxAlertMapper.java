package com.hdu.email.mybatis.mapper;

import com.hdu.emailservice.dto.InboxAlertDto;

public interface InboxAlertMapper {
    Integer selNumberByUsername(String username);
    Integer insAlert(InboxAlertDto inboxAlertDto);
    Integer updAlert(InboxAlertDto inboxAlertDto);
}
