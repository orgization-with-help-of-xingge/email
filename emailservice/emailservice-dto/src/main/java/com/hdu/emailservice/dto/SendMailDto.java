package com.hdu.emailservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class SendMailDto implements Serializable {
        private List<FileDto> fileLists;
        private List<String> recipients;
        private List<String> copys;
        private String title;
        private String content;
}
