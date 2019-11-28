package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SendMailDto implements Serializable {
//        private List<FileDto> fileLists;
        private String recipients;
        private String copys;
        private String title;
        private String content;
}
