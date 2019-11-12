package com.hdu.emailservice.provider;

import com.hdu.email.common.util.transfer.PageView;
import com.hdu.emailservice.api.InboxApi;
import com.hdu.emailservice.biz.service.InboxService;
import com.hdu.emailservice.dto.Inbox;
import com.hdu.emailservice.dto.InboxParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InboxProvider implements InboxApi {
    @Autowired
    private InboxService inboxService;

    @Override
    public PageView<Inbox> getUnReadEmail(InboxParam param) {
        PageView<Inbox> pageView = null;
        try {
            pageView = inboxService.queryUnReadEmail(param);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return pageView;
    }
}
