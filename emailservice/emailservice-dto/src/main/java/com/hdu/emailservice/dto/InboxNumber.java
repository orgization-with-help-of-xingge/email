package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class InboxNumber implements Serializable {
    private Integer draftcount;
    private Integer inboxcount;
    private Integer outboxcount;


}
