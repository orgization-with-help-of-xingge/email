package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能描述: 提醒事件dto
 * @Author: sixl
 * @Date: 2019/12/7 13:46
 */
@Getter
@Setter
public class InboxAlertDto implements Serializable {
    private String urid;
    private String username;
    private Integer inboxNumber;
}
